package ro.uaic.info.querybackendservice.model;

import org.eclipse.rdf4j.model.IRI;

import static org.eclipse.rdf4j.model.util.Values.iri;

public class ObjectType {

    public final static IRI NA_BEVERAGE = iri(IRILabel.NA_BEVERAGE);
    public final static IRI BEVERAGE_CTX = iri(IRILabel.BEVERAGE_CTX);
    public final static IRI CTX_LOCATION = iri(IRILabel.CTX_LOCATION);
    public final static IRI CTX_SEASON = iri(IRILabel.CTX_SEASON);
    public final static IRI CTX_EVENT = iri(IRILabel.CTX_EVENT);
    public final static IRI CTX_HRS = iri(IRILabel.CTX_HRS);
    public final static IRI DESCRIBES_HRS = iri(IRILabel.DESCRIBES_HRS);
    public final static IRI USER_PROFILE = iri(IRILabel.USER_PROFILE);
    public final static IRI FROM = iri(IRILabel.FROM);
    public final static IRI PREFERENCE = iri(IRILabel.PREFERENCE);
    public final static IRI ALLERGENS = iri(IRILabel.ALLERGENS);
}
