package ro.uaic.info.querybackendservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.LOCN;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.TIME;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.TriplePattern;
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
import java.util.stream.Collectors;

import static ro.uaic.info.querybackendservice.model.BeverageContext.BEVERAGE;
import static ro.uaic.info.querybackendservice.model.BeverageContext.BEVERAGE_CTX_ID;
import static ro.uaic.info.querybackendservice.model.BeverageContext.EVENT;
import static ro.uaic.info.querybackendservice.model.BeverageContext.IS_PREFERRED;
import static ro.uaic.info.querybackendservice.model.BeverageContext.LOCATION;
import static ro.uaic.info.querybackendservice.model.BeverageContext.SEASON;

/**
 * Using RDF4JDao to experiment with using the connection directly
 */
@Component
@Slf4j
public class BeverageContextDao extends SimpleRDF4JCRUDDao<BeverageContext, IRI> {


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
                .add(BEVERAGE, context.getBeverage())
                .add(IS_PREFERRED, context.isContextBeveragePreferred())
                .add(EVENT, context.getEvent())
                .add(LOCATION, context.getLocation())
                .add(SEASON, context.getSeason());
    }

    @Override
    protected BeverageContext mapSolution(BindingSet querySolution) {
        BeverageContext context = new BeverageContext();
        context.setId(QueryResultUtils.getIRI(querySolution, BEVERAGE_CTX_ID));
        context.setBeverage(QueryResultUtils.getIRI(querySolution, BEVERAGE));
        context.setContextBeveragePreferred(QueryResultUtils.getBoolean(querySolution, IS_PREFERRED));
        context.setEvent(QueryResultUtils.getStringOptional(querySolution, EVENT).orElse(null));
        context.setLocation(QueryResultUtils.getStringOptional(querySolution, LOCATION).orElse(null));
        context.setSeason(QueryResultUtils.getStringOptional(querySolution, SEASON).orElse(null));
        return context;
    }

    @Override
    protected String getReadQuery() {
        SelectQuery selectQuery = Queries.SELECT();

        String readQuery = selectQuery.select(
                BEVERAGE_CTX_ID, BEVERAGE, IS_PREFERRED, EVENT, LOCATION, SEASON)
                .where(
                        BEVERAGE_CTX_ID.isA(OWL.CLASS)
                                .andHas(FOAF.KNOWS, BEVERAGE)
                                .andHas(RDFS.LABEL, IS_PREFERRED)
                                .and(BEVERAGE_CTX_ID.has(ObjectType.CTX_EVENT, EVENT).optional())
                                .and(BEVERAGE_CTX_ID.has(ObjectType.CTX_LOCATION, LOCATION).optional())
                                .and(BEVERAGE_CTX_ID.has(ObjectType.CTX_SEASON, SEASON).optional())
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
                Queries.INSERT(
                     BEVERAGE_CTX_ID.isA(OWL.CLASS)
                            .andHas(FOAF.KNOWS, BEVERAGE)
                            .andHas(RDFS.LABEL, IS_PREFERRED)
                            .andHas(ObjectType.CTX_EVENT, EVENT)
                            .andHas(ObjectType.CTX_LOCATION, LOCATION)
                            .andHas(ObjectType.CTX_SEASON, SEASON)
                        )
                .getQueryString());
    }

    public Map<IRI, List<BeverageContext>> groupById() {
        return list().stream().collect(Collectors.groupingBy(
                BeverageContext::getId
        ));
    }
}
