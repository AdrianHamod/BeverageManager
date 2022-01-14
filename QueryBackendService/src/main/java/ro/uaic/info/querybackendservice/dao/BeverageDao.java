package ro.uaic.info.querybackendservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.spring.dao.SimpleRDF4JCRUDDao;
import org.eclipse.rdf4j.spring.dao.support.bindingsBuilder.MutableBindings;
import org.eclipse.rdf4j.spring.dao.support.sparql.NamedSparqlSupplier;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.eclipse.rdf4j.spring.util.QueryResultUtils;
import org.springframework.stereotype.Component;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.ObjectType;

import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static ro.uaic.info.querybackendservice.model.Beverage.BEVERAGE_ID;
import static ro.uaic.info.querybackendservice.model.Beverage.NAME;
import static ro.uaic.info.querybackendservice.model.Beverage.PARENT;

@Component
@Slf4j
public class BeverageDao extends SimpleRDF4JCRUDDao<Beverage, IRI> {

    static abstract class QUERY_KEYS {
        public static final String ALL_BEVERAGES = "beverages";
        public static final String BEVERAGES_BY_TYPE = "beveragesByType";
    }

    public BeverageDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    @Override
    protected void populateIdBindings(MutableBindings mutableBindings, IRI iri) {
        mutableBindings.add(BEVERAGE_ID, iri);

    }

    @Override
    protected void populateBindingsForUpdate(MutableBindings bindingsBuilder, Beverage beverage) {
        bindingsBuilder
                .add(NAME, beverage.getName())
                .add(PARENT, beverage.getParent());
    }

    @Override
    protected Beverage mapSolution(BindingSet querySolution) {
        Beverage beverage = new Beverage();
        beverage.setBeverageId(iri(QueryResultUtils.getString(querySolution, BEVERAGE_ID)));
        beverage.setName(QueryResultUtils.getString(querySolution, NAME));
        beverage.setParent(QueryResultUtils.getIRI(querySolution, PARENT));
        return beverage;
    }

    @Override
    protected String getReadQuery() {
        SelectQuery selectQuery = Queries.SELECT();
//        Prefix ex = SparqlBuilder.prefix(iri("http://www.bem.ro/bem-schema/"));
        String readQuery = selectQuery.select(BEVERAGE_ID, NAME, PARENT)
                .where(BEVERAGE_ID.isA(ObjectType.NA_BEVERAGE)
                        .andHas(FOAF.NAME, NAME)
                        .andHas(RDFS.SUBCLASSOF, PARENT)
                )
                .getQueryString();
        log.info("[READ_QUERY] {}", readQuery);
        return readQuery;
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer preparer) {
        return preparer.forKey(QUERY_KEYS.ALL_BEVERAGES)
                .supplySparql(Queries.SELECT(BEVERAGE_ID).where(
                        BEVERAGE_ID.isA(ObjectType.NA_BEVERAGE)
                ).getQueryString())
                .forKey(QUERY_KEYS.BEVERAGES_BY_TYPE)
                .supplySparql(Queries.SELECT(BEVERAGE_ID, PARENT).where(
                        BEVERAGE_ID.isA((ObjectType.NA_BEVERAGE))
                ).getQueryString());
    }

    @Override
    protected IRI getInputId(Beverage beverage) {
        return beverage.getBeverageId();
    }

    @Override
    protected NamedSparqlSupplier getInsertSparql(Beverage beverage) {
        return NamedSparqlSupplier.of("insert", () -> Queries.INSERT(BEVERAGE_ID.isA(ObjectType.NA_BEVERAGE)
                        .andHas(FOAF.NAME, NAME)
                        .andHas(RDFS.SUBCLASSOF, PARENT)
                )
                .getQueryString());
    }


    public List<Beverage> listBeverages() {
        return getNamedTupleQuery(QUERY_KEYS.ALL_BEVERAGES)
                .evaluateAndConvert()
                .toStream()
                .map(bs -> QueryResultUtils.getIRI(bs, BEVERAGE_ID))
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<Beverage> listBeveragesByType(IRI type) {
        return getNamedTupleQuery(QUERY_KEYS.BEVERAGES_BY_TYPE)
                .evaluateAndConvert()
                .toStream()
                .map(bs -> QueryResultUtils.getIRI(bs, BEVERAGE_ID))
                .map(this::getById)
                .filter(bs -> type.stringValue().equals(String.valueOf(bs.getParent())))
                .collect(Collectors.toList());
    }
}
