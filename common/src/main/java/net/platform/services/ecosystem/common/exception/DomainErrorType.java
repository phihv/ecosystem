package net.platform.services.ecosystem.common.exception;

public enum DomainErrorType {
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", "The resource already exists."),
    INVALID_ARGUMENT("INVALID_ARGUMENT", "One or more arguments are invalid."),
    NOT_FOUND("NOT_FOUND", "Resource not found."),
    BUSINESS_RULE_VIOLATION("BUSINESS_RULE_VIOLATION", "A business rule has been violated."),
    INTERNAL_ERROR("INTERNAL_ERROR", "An internal error occurred.");

    private final String type;
    private final String defaultMessage;

    private DomainErrorType(String code, String defaultMessage) {
        this.type = code;
        this.defaultMessage = defaultMessage;
    }

    public String getType() {
        return this.type;
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }
}
