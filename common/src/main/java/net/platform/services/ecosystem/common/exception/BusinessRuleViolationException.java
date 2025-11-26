package net.platform.services.ecosystem.common.exception;

public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String customClientMessage, Integer errorCode, Throwable cause) {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION, customClientMessage, errorCode, cause);
    }

    public BusinessRuleViolationException(String customClientMessage, Throwable cause) {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION, customClientMessage, cause);
    }

    public BusinessRuleViolationException(String customClientMessage, Integer errorCode) {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION, customClientMessage, errorCode);
    }

    public BusinessRuleViolationException(String customClientMessage) {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION, customClientMessage);
    }

    public BusinessRuleViolationException() {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION);
    }

    public BusinessRuleViolationException(Throwable cause) {
        super(DomainErrorType.BUSINESS_RULE_VIOLATION, cause);
    }
}
