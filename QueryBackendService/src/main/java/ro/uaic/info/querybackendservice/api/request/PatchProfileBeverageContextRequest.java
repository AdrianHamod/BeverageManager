package ro.uaic.info.querybackendservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchProfileBeverageContextRequest {

    private String beverage;
    private boolean isContextBeveragePreferred;
    private String event;
    private String location;
    private String season;
}
