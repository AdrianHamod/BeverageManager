package ro.uaic.info.querybackendservice.api.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetails {

    private String code;
    private String defaultMessage;
}

