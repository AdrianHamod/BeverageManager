package ro.uaic.info.querybackendservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeverageContext {

    public static final Variable BEVERAGE_CTX_ID = SparqlBuilder.var("beverage_ctx_id");
    public static final Variable IS_PREFERRED = SparqlBuilder.var("beverage_ctx_is_preferred");
    public static final Variable BEVERAGE = SparqlBuilder.var("beverage_context_beverage");
    public static final Variable EVENT = SparqlBuilder.var("beverage_ctx_event");
    public static final Variable LOCATION = SparqlBuilder.var("beverage_ctx_location");
    public static final Variable SEASON = SparqlBuilder.var("beverage_ctx_season");

    private IRI id;
    private IRI beverage;
    private boolean isContextBeveragePreferred;
    private String event;
    private String location;
    private String season;

    @JsonProperty(value = "isContextBeveragePreferred")
    public boolean isContextBeveragePreferred() {
        return isContextBeveragePreferred;
    }
}
