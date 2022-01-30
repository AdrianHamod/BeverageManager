package ro.uaic.info.querybackendservice.api.request;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.uaic.info.querybackendservice.model.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutProfileRequest {
    private String username;
    private int age;
    private Gender gender;
    private CountryCode countryCode;
}
