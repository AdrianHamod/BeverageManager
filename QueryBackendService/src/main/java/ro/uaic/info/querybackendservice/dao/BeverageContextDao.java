package ro.uaic.info.querybackendservice.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.spring.dao.SimpleRDF4JCRUDDao;
import org.eclipse.rdf4j.spring.dao.support.bindingsBuilder.MutableBindings;
import org.eclipse.rdf4j.spring.dao.support.sparql.NamedSparqlSupplier;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.eclipse.rdf4j.spring.util.QueryResultUtils;
import org.springframework.stereotype.Component;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.ObjectType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ro.uaic.info.querybackendservice.model.BeverageContext.BEVERAGE_CTX_ID;
import static ro.uaic.info.querybackendservice.model.BeverageContext.EVENT;
import static ro.uaic.info.querybackendservice.model.BeverageContext.HEALTH_RESTRICTIONS;
import static ro.uaic.info.querybackendservice.model.BeverageContext.LOCATION;
import static ro.uaic.info.querybackendservice.model.BeverageContext.SEASON;

/**
 * Using RDF4JDao to experiment with using the connection directly
 */
@Component
@Slf4j
public class BeverageContextDao extends SimpleRDF4JCRUDDao<BeverageContext, IRI> {

    private final ObjectMapper mapper = new ObjectMapper();

    static abstract class QUERY_KEYS {
        public static final String BEVERAGE_CONTEXT_BY_ID = "beverage-ctx-by-id";
    }

    public BeverageContextDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    @Override
    protected void populateIdBindings(MutableBindings mutableBindings, IRI iri) {
        mutableBindings.add(BEVERAGE_CTX_ID, iri);
    }

    @Override
    protected void populateBindingsForUpdate(MutableBindings bindingsBuilder, BeverageContext context) {
        bindingsBuilder
                .add(EVENT, context.getEvent())
                .add(LOCATION, context.getLocation())
                .add(SEASON, context.getSeason());
        try {
            bindingsBuilder.add(HEALTH_RESTRICTIONS, mapper.writeValueAsString(context.getHealthRestriction()));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Unable to serialize health restriction set");
        }
    }

    @Override
    protected BeverageContext mapSolution(BindingSet querySolution) {
        BeverageContext context = new BeverageContext();
        context.setId(QueryResultUtils.getIRI(querySolution, BEVERAGE_CTX_ID));
        context.setEvent(QueryResultUtils.getString(querySolution, EVENT));
        context.setLocation(QueryResultUtils.getString(querySolution, LOCATION));
        context.setSeason(QueryResultUtils.getString(querySolution, SEASON));

        String hrs = QueryResultUtils.getString(querySolution, HEALTH_RESTRICTIONS);

        try {
            Set<String> hrsSet = mapper.readValue(
                    hrs, new TypeReference<Set<String>>() {
                    });
            context.setHealthRestriction(hrsSet);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Unable to deserialize health restriction set");
        }

        return context;
    }

    @Override
    protected String getReadQuery() {
        SelectQuery selectQuery = Queries.SELECT();
        String readQuery = selectQuery.select(
                BEVERAGE_CTX_ID, EVENT, LOCATION, SEASON, HEALTH_RESTRICTIONS)
                .where(BEVERAGE_CTX_ID.isA(ObjectType.BEVERAGE_CTX)
                        .andHas(ObjectType.CTX_EVENT, EVENT)
                        .andHas(ObjectType.CTX_LOCATION, LOCATION)
                        .andHas(ObjectType.CTX_SEASON, SEASON)
                        .andHas(ObjectType.DESCRIBES_HRS, HEALTH_RESTRICTIONS)
                )
                .getQueryString();
        log.info("[READ_QUERY] {}", readQuery);
        return readQuery;
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer namedSparqlSupplierPreparer) {
        return namedSparqlSupplierPreparer.forKey(QUERY_KEYS.BEVERAGE_CONTEXT_BY_ID)
                .supplySparql(Queries.SELECT(BEVERAGE_CTX_ID)
                        .where(BEVERAGE_CTX_ID.isA(ObjectType.BEVERAGE_CTX))
                        .getQueryString()
                );
    }

    @Override
    protected IRI getInputId(BeverageContext context) {
        return context.getId();
    }

    @Override
    protected NamedSparqlSupplier getInsertSparql(BeverageContext beverage) {
        return NamedSparqlSupplier.of("insert", () ->
                Queries.INSERT(BEVERAGE_CTX_ID.isA(ObjectType.BEVERAGE_CTX)
                            .andHas(ObjectType.CTX_EVENT, EVENT)
                            .andHas(ObjectType.CTX_LOCATION, LOCATION)
                            .andHas(ObjectType.CTX_SEASON, SEASON)
                            .andHas(ObjectType.DESCRIBES_HRS, HEALTH_RESTRICTIONS)
                )
                .getQueryString());
    }

    public Map<IRI, List<BeverageContext>> groupById() {
        return list().stream().collect(Collectors.groupingBy(
                BeverageContext::getId
        ));
    }
}
