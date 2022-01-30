package ro.uaic.info.querybackendservice.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ro.uaic.info.querybackendservice.api.errors.ErrorData;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.BeverageContext;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@JsonInclude(NON_NULL)
public class GetBeverageByIdResponse {

    private Beverage beverage;
    private BeverageContext beverageContext;
    private ErrorData errorData;
}
