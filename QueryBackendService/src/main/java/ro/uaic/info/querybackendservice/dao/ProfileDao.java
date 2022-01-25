package ro.uaic.info.querybackendservice.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.i18n.CountryCode;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.spring.dao.SimpleRDF4JCRUDDao;
import org.eclipse.rdf4j.spring.dao.support.bindingsBuilder.MutableBindings;
import org.eclipse.rdf4j.spring.dao.support.sparql.NamedSparqlSupplier;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.eclipse.rdf4j.spring.util.QueryResultUtils;
import org.springframework.stereotype.Component;
import ro.uaic.info.querybackendservice.model.Gender;
import ro.uaic.info.querybackendservice.model.ObjectType;
import ro.uaic.info.querybackendservice.model.Profile;

import java.util.Map;
import java.util.Set;

import static ro.uaic.info.querybackendservice.model.Profile.AGE;
import static ro.uaic.info.querybackendservice.model.Profile.BEVERAGE_PREFERENCES;
import static ro.uaic.info.querybackendservice.model.Profile.COUNTRY_CODE;
import static ro.uaic.info.querybackendservice.model.Profile.GENDER;
import static ro.uaic.info.querybackendservice.model.Profile.ID;
import static ro.uaic.info.querybackendservice.model.Profile.USERNAME;

@Component
@Slf4j
public class ProfileDao extends SimpleRDF4JCRUDDao<Profile, IRI> {

    private final ObjectMapper mapper = new ObjectMapper();

    public ProfileDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    @Override
    protected void populateIdBindings(MutableBindings bindingsBuilder, IRI iri) {
        bindingsBuilder.add(ID, iri);
    }

    @Override
    protected void populateBindingsForUpdate(MutableBindings bindingsBuilder, Profile profile) {
        bindingsBuilder
                .add(USERNAME, profile.getUsername())
                .add(AGE, profile.getAge())
                .add(GENDER, profile.getGender().name())
                .add(COUNTRY_CODE, profile.getCountryCode().name());

        try {
            bindingsBuilder.add(BEVERAGE_PREFERENCES, mapper.writeValueAsString(profile.getBeveragePreferences()));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Unable to serialize beverage preferences");
        }
    }

    @Override
    protected Profile mapSolution(BindingSet querySolution) {
        Profile profile = new Profile();
        profile.setId(QueryResultUtils.getIRI(querySolution, ID));
        profile.setUsername(QueryResultUtils.getString(querySolution, USERNAME));
        profile.setAge(Integer.parseInt(QueryResultUtils.getString(querySolution, AGE)));
        profile.setGender(Gender.valueOf(QueryResultUtils.getString(querySolution, GENDER)));
        profile.setCountryCode(
                CountryCode.getByCode(
                QueryResultUtils.getString(querySolution, COUNTRY_CODE)));

        String beveragePref = QueryResultUtils.getString(querySolution, BEVERAGE_PREFERENCES);

        try {
            Map<Boolean, Set<IRI>> beveragePrefMapping = mapper.readValue(
                    beveragePref, new TypeReference<Map<Boolean, Set<IRI>>>() {
                    });
            profile.setBeveragePreferences(beveragePrefMapping);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Unable to deserialize beverage preferences to a map");
        }

        return profile;
    }

    @Override
    protected String getReadQuery() {
        SelectQuery selectQuery = Queries.SELECT();
        String readQuery = selectQuery.select(
                        ID, USERNAME, AGE, GENDER, COUNTRY_CODE, BEVERAGE_PREFERENCES)
                .where(ID.isA(ObjectType.USER_PROFILE)
                        .andHas(FOAF.NAME, USERNAME)
                        .andHas(FOAF.AGE, AGE)
                        .andHas(FOAF.GENDER, GENDER)
                        .andHas(ObjectType.FROM, COUNTRY_CODE)
                        .andHas(ObjectType.PREFERENCE, BEVERAGE_PREFERENCES)
                )
                .getQueryString();
        log.info("[READ_QUERY] {}", readQuery);
        return readQuery;
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer preparer) {
        return null;
    }

    @Override
    protected IRI getInputId(Profile profile) {
        return profile.getId();
    }

    @Override
    protected NamedSparqlSupplier getInsertSparql(Profile profile) {
        return NamedSparqlSupplier.of("insert", () ->
                Queries.INSERT(ID.isA(ObjectType.USER_PROFILE)
                                .andHas(FOAF.NAME, USERNAME)
                                .andHas(FOAF.AGE, AGE)
                                .andHas(FOAF.GENDER, GENDER)
                                .andHas(ObjectType.FROM, COUNTRY_CODE)
                                .andHas(ObjectType.PREFERENCE, BEVERAGE_PREFERENCES)
                        )
                        .getQueryString());
    }
}
