package ro.uaic.info.querybackendservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBeverageRequest {

    @NotEmpty
    @NotNull
    private String beverageName;
    private String parentName;
    private String description;
    private String season;
    private String location;
    private String event;
    private Set<String> healthRestriction;
}
