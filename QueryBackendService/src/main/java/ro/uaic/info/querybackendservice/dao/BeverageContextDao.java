package ro.uaic.info.querybackendservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Triple;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.DynamicModelFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.RDFContainers;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.spring.dao.RDF4JDao;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.springframework.stereotype.Component;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.IRILabel;
import ro.uaic.info.querybackendservice.model.ObjectType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ro.uaic.info.querybackendservice.model.BeverageContext.BEVERAGE_CTX_ID;
import static ro.uaic.info.querybackendservice.model.ObjectType.CTX_HRS;

/**
 * Using RDF4JDao to experiment with using the connection directly
 */
@Component
@Slf4j
public class BeverageContextDao extends RDF4JDao {

    public BeverageContextDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    static abstract class QUERY_KEYS {
        public static final String BEVERAGE_CONTEXT_BY_ID = "beverage-ctx-by-id";
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer namedSparqlSupplierPreparer) {
        return namedSparqlSupplierPreparer.forKey(QUERY_KEYS.BEVERAGE_CONTEXT_BY_ID)
                .supplySparql(Queries.SELECT(BEVERAGE_CTX_ID)
                        .where(BEVERAGE_CTX_ID.isA(ObjectType.BEVERAGE_CTX))
                        .getQueryString()
                );
    }

    public BeverageContext save(BeverageContext beverageContext) {
        ValueFactory factory = Values.getValueFactory();
        Model beverageContextModel = (new DynamicModelFactory()).createEmptyModel();
        Statement eventStatement = factory.createStatement(
                beverageContext.getId(),
                ObjectType.CTX_EVENT,
                factory.createLiteral(beverageContext.getEvent()));

        Statement locationStatement = factory.createStatement(
                beverageContext.getId(),
                ObjectType.CTX_LOCATION,
                factory.createLiteral(beverageContext.getLocation()));

        Statement seasonStatement = factory.createStatement(
                beverageContext.getId(),
                ObjectType.CTX_SEASON,
                factory.createLiteral(beverageContext.getSeason()));

        Statement typeStatement = factory.createStatement(
                beverageContext.getId(),
                RDF.TYPE,
                ObjectType.BEVERAGE_CTX);


        beverageContextModel.add(typeStatement);
        beverageContextModel.add(eventStatement);
        beverageContextModel.add(locationStatement);
        beverageContextModel.add(seasonStatement);

        Set<String> hrs = beverageContext.getHealthRestriction();
        Collection<Statement> hrsStatements = RDFContainers.toRDF(RDF.BAG, hrs, CTX_HRS, new TreeModel());
        // CAST EXCEPTION TRIPLE TO LITERAL; Model api allows me to do this, but the repository does not like it
//        for (Statement stmt :
//                hrsStatements) {
//            Triple hrsTriple = factory.createTriple(stmt.getSubject(), stmt.getPredicate(), stmt.getObject());
//            beverageContextModel.add(factory.createStatement(
//                    beverageContext.getId(),
//                    DESCRIBES_HRS,
//                    hrsTriple
//            ));
//        }

        getRdf4JTemplate().consumeConnection(con -> con.add(beverageContextModel));

        return beverageContext;
    }

    public BeverageContext getBeverageContextById(IRI id) {
        Model beverageById = QueryResults.asModel(getAllStatementsById(id));
        return map(beverageById);
    }

    protected RepositoryResult<Statement> getAllStatementsById(IRI id) {
        return getRdf4JTemplate().applyToConnection(con -> con.getStatements(id, null, null));
        /*
         * return getRdf4JTemplate().applyToConnection(con -> con.getStatements(null, null, null));
         * Commented call works as expected, fetching all BeveragesContext statements.
         * Uncommented call by id returns nothing.
         */
    }
    
    public BeverageContext map(Model beverageModel) {
        BeverageContext beverageContext = new BeverageContext();
        Set<String> healthRestrictions = new HashSet<>();
        for (Statement stmt :
                beverageModel) {
            log.info("[BeverageContext] {} {} {}", stmt.getSubject(), stmt.getPredicate(), stmt.getObject());
            switch (String.valueOf(stmt.getPredicate())) {
                case "http://www.w3.org/1999/02/22-rdf-syntax-ns#type":
                    beverageContext.setId((IRI) stmt.getSubject());
                    break;
                case IRILabel.CTX_EVENT:
                    beverageContext.setEvent(stmt.getObject().stringValue());
                    break;
                case IRILabel.CTX_LOCATION:
                    beverageContext.setLocation(stmt.getObject().stringValue());
                    break;
                case IRILabel.CTX_SEASON:
                    beverageContext.setSeason(stmt.getObject().stringValue());
                    break;
                case IRILabel.DESCRIBES_HRS:
                    Triple hrs = (Triple) stmt.getObject();
                    if (hrs.getPredicate().equals(RDFS.MEMBER)) {
                        healthRestrictions.add(hrs.getObject().stringValue());
                    }
                    break;
            }

        }
        beverageContext.setHealthRestriction(healthRestrictions);
        return beverageContext;
    }
}
