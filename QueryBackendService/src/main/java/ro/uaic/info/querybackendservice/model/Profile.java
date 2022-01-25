package ro.uaic.info.querybackendservice.model;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

import static org.eclipse.rdf4j.model.util.Values.iri;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profile {

    public static final Variable ID = SparqlBuilder.var("profile_id");
    public static final Variable USERNAME = SparqlBuilder.var("profile_username");
    public static final Variable AGE = SparqlBuilder.var("profile_age");
    public static final Variable GENDER = SparqlBuilder.var("profile_gender");
    public static final Variable COUNTRY_CODE = SparqlBuilder.var("profile_country_code");
    public static final Variable BEVERAGE_PREFERENCES = SparqlBuilder.var("profile_beverage_preferences");

    private IRI id;
    @NotNull
    private String username;
    @NotNull
    private int age;
    @NotNull
    private Gender gender;
    @NotNull
    private CountryCode countryCode;
    private Map<Boolean, Set<IRI>> beveragePreferences;

    public Profile(
            String username,
            int age,
            Gender gender,
            CountryCode countryCode,
            Map<Boolean, Set<IRI>> beveragePreferences) {
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
