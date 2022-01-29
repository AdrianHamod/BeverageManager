package ro.uaic.info.querybackendservice.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ro.uaic.info.querybackendservice.api.errors.ErrorData;
import ro.uaic.info.querybackendservice.model.Beverage;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@JsonInclude(NON_NULL)
public class PostBeverageResponse {

    private Beverage beverage;
    private ErrorData errorData;
}

