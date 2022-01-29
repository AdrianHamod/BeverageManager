package ro.uaic.info.querybackendservice.model;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;

import java.util.List;

import static org.eclipse.rdf4j.model.util.Values.iri;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    public static final Variable ID = SparqlBuilder.var("profile_id");
    public static final Variable USERNAME = SparqlBuilder.var("profile_username");
    public static final Variable AGE = SparqlBuilder.var("profile_age");
    public static final Variable GENDER = SparqlBuilder.var("profile_gender");
    public static final Variable COUNTRY_CODE = SparqlBuilder.var("profile_country_code");
    public static final Variable BEVERAGE_PREFERENCES = SparqlBuilder.var("profile_beverage_preferences");

    private IRI id;
    private String username;
    private int age;
    private Gender gender;
    private CountryCode countryCode;
    private List<BeverageContext> beveragePreferences;

    public Profile(
            String username,
            int age,
            Gender gender,
            CountryCode countryCode,
            List<BeverageContext> beveragePreferences) {
        this(
                iri(IRILabel.NS, username),
                username,
                age,
                gender,
                countryCode,
                beveragePreferences
        );
    }


}
