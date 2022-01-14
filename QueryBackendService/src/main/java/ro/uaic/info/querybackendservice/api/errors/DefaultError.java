package ro.uaic.info.querybackendservice.api.errors;

public enum DefaultError {

    InvalidInputMultipleBeverageNames(new ErrorDetails("400", "Expected exactly 1 value for the beverage name")),
    InvalidInputMissingBeverageName(new ErrorDetails("400", "Beverage name is mandatory"));

    public final ErrorDetails details;

    DefaultError(ErrorDetails details) {
        this.details = details;
    }
}
