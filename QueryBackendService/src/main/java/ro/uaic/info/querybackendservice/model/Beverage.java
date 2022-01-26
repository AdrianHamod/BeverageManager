package ro.uaic.info.querybackendservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Beverage {

    public static final Variable BEVERAGE_ID = SparqlBuilder.var("beverage_id");
    public static final Variable NAME = SparqlBuilder.var("beverage_name");
    public static final Variable PARENT = SparqlBuilder.var("beverage_parent");
    public static final Variable HRS = SparqlBuilder.var("beverage_health_restrictions");
    public static final Variable DESCRIPTION = SparqlBuilder.var("beverage_description");
    public static final Variable CONTEXT = SparqlBuilder.var("beverage_context");

    private IRI beverageId;
    private String name;
    private IRI parent;
    private String description;
    private BeverageContext context;

}
