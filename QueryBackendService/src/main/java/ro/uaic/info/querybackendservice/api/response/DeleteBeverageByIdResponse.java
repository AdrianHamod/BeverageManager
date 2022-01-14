package ro.uaic.info.querybackendservice.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.eclipse.rdf4j.model.IRI;
import ro.uaic.info.querybackendservice.api.errors.ErrorData;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@JsonInclude(NON_NULL)
public class DeleteBeverageByIdResponse {

    private IRI id;
    private ErrorData errorData;
}
