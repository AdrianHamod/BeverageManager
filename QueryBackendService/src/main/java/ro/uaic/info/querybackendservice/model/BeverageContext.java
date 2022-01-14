package ro.uaic.info.querybackendservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BeverageContext {

    public static final Variable BEVERAGE_CTX_ID = SparqlBuilder.var("beverage_ctx_id");
    public static final Variable EVENT = SparqlBuilder.var("beverage_ctx_event");
    public static final Variable LOCATION = SparqlBuilder.var("beverage_ctx_location");
    public static final Variable SEASON = SparqlBuilder.var("beverage_ctx_season");
    public static final Variable HEALTH_RESTRICTIONS = SparqlBuilder.var("beverage_ctx_hrs");

    private IRI id;
    private String event;
    private String location;
    private String season;
    private Set<String> healthRestriction;

    @Override
    public String toString() {
        return "BeverageContext{" +
                "id=" + id +
                ", event='" + event + '\'' +
                ", location='" + location + '\'' +
                ", season='" + season + '\'' +
                ", healthRestriction=" + healthRestriction +
                '}';
    }
}
