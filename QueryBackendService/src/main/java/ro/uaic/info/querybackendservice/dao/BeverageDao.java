package ro.uaic.info.querybackendservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.DC;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
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
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.ObjectType;

import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;
import static ro.uaic.info.querybackendservice.model.Beverage.ALLERGENS;
import static ro.uaic.info.querybackendservice.model.Beverage.BEVERAGE_ID;
import static ro.uaic.info.querybackendservice.model.Beverage.DESCRIPTION;
import static ro.uaic.info.querybackendservice.model.Beverage.IMAGE_URL;
import static ro.uaic.info.querybackendservice.model.Beverage.NAME;
import static ro.uaic.info.querybackendservice.model.Beverage.PARENT;

@Component
@Slf4j
public class BeverageDao extends SimpleRDF4JCRUDDao<Beverage, IRI> {

    static abstract class QUERY_KEYS {
        public static final String DESCRIPTION_FULL_TEXT_SEARCH = "descriptionSearch";
        public static final String LIST_CHILDREN = "listChildren";
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
                .add(PARENT, beverage.getParent())
                .add(DESCRIPTION, beverage.getDescription())
                .add(IMAGE_URL, beverage.getImageUrl());

        bindingsBuilder.add(ALLERGENS, String.join(", ", beverage.getAllergens()));
    }

    @Override
    protected Beverage mapSolution(BindingSet querySolution) {
        Beverage beverage = new Beverage();
        beverage.setBeverageId(iri(QueryResultUtils.getString(querySolution, BEVERAGE_ID)));
        beverage.setName(QueryResultUtils.getStringMaybe(querySolution, NAME));
        beverage.setParent(QueryResultUtils.getIRI(querySolution, PARENT));
        beverage.setDescription(QueryResultUtils.getString(querySolution, DESCRIPTION));
        beverage.setImageUrl(QueryResultUtils.getStringMaybe(querySolution, IMAGE_URL));
        beverage.setAllergens(
                List.of(QueryResultUtils.getStringOptional(querySolution, ALLERGENS)
                        .orElse("").split(", "))
        );
        return beverage;
    }

    @Override
    protected String getReadQuery() {
        SelectQuery selectQuery = Queries.SELECT();
        String readQuery = selectQuery.select(BEVERAGE_ID, NAME, PARENT, DESCRIPTION, NAME, IMAGE_URL, ALLERGENS)
                .where(
                        BEVERAGE_ID.isA(OWL.CLASS)
                                .andHas(RDFS.SUBCLASSOF, PARENT)
                                .andHas(DC.DESCRIPTION, DESCRIPTION)
                                .and(BEVERAGE_ID.has(FOAF.NAME, NAME).optional())
                                .and(BEVERAGE_ID.has(ObjectType.IMAGE, IMAGE_URL).optional())
                                .and(BEVERAGE_ID.has(ObjectType.ALLERGENS, ALLERGENS).optional())
                )
                .getQueryString();
        log.info("[READ_QUERY] {}", readQuery);
        return readQuery;
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer preparer) {
        String descriptionFullTextSearch = "PREFIX search: <http://www.openrdf.org/contrib/lucenesail#> " +
                "SELECT ?beverage_id ?beverage_description " +
                "WHERE { ?beverage_id search:matches [" +
                " search:query ?term ; " +
                " search:snippet ?beverage_description ] } ";

        String listAllChildren = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
                "SELECT ?beverage_id " +
                "WHERE { ?beverage_id rdfs:subClassOf ?beverage_parent . } ";

        return preparer.forKey(QUERY_KEYS.DESCRIPTION_FULL_TEXT_SEARCH)
                .supplySparql(descriptionFullTextSearch)
                .forKey(QUERY_KEYS.LIST_CHILDREN)
                .supplySparql(listAllChildren);
    }

    @Override
    protected IRI getInputId(Beverage beverage) {
        return beverage.getBeverageId();
    }

    @Override
    protected NamedSparqlSupplier getInsertSparql(Beverage beverage) {
        return NamedSparqlSupplier.of("insert", () -> Queries.INSERT(
                        (TriplePattern) BEVERAGE_ID.isA(OWL.CLASS)
                                .andHas(RDFS.SUBCLASSOF, PARENT)
                                .andHas(DC.DESCRIPTION, DESCRIPTION)
                                .and(BEVERAGE_ID.has(FOAF.NAME, NAME).optional())
                                .and(BEVERAGE_ID.has(ObjectType.IMAGE, IMAGE_URL).optional())
                                .and(BEVERAGE_ID.has(ObjectType.ALLERGENS, ALLERGENS).optional())
                )
                .getQueryString());
    }

    public List<Beverage> searchBeveragesMatchingDescription(String term) {
        return getNamedTupleQuery(QUERY_KEYS.DESCRIPTION_FULL_TEXT_SEARCH)
                .withBinding("term", literal(term + "*"))
                .evaluateAndConvert()
                .toStream()
                .map(bs -> QueryResultUtils.getIRI(bs, BEVERAGE_ID))
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<Beverage> listChildren(IRI id) {
        return getNamedTupleQuery(QUERY_KEYS.LIST_CHILDREN)
                .withBinding("beverage_parent", id)
                .evaluateAndConvert()
                .toStream()
                .map(bs -> QueryResultUtils.getIRI(bs, BEVERAGE_ID))
                .map(this::getById)
                .collect(Collectors.toList());
    }
}
