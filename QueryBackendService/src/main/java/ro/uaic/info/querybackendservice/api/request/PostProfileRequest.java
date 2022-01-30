package ro.uaic.info.querybackendservice.api.request;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.Gender;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProfileRequest {

    @NotNull
    @NotEmpty
    private String username;
    @Min(value = 8)
    @Max(value = 123)
    private int age;
    @NotNull
    private Gender gender;
    private String countryCode;
    private List<BeverageContext> beveragePreferences;
}
