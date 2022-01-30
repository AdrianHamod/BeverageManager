package ro.uaic.info.querybackendservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBeverageRequest {

    @NotEmpty
    @NotNull
    private String beverageName;
    private String parentName;
    private String description;
    private String image;
    private List<String> allergens;
}
