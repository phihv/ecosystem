package net.platform.services.ecosystem.common.exception;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String customClientMessage, Integer errorCode, Throwable cause) {
        super(DomainErrorType.NOT_FOUND, customClientMessage, errorCode, cause);
    }

    public ResourceNotFoundException(String customClientMessage, Throwable cause) {
        super(DomainErrorType.NOT_FOUND, customClientMessage, cause);
    }

    public ResourceNotFoundException(String customClientMessage, Integer errorCode) {
        super(DomainErrorType.NOT_FOUND, customClientMessage, errorCode);
    }

    public ResourceNotFoundException(String customClientMessage) {
        super(DomainErrorType.NOT_FOUND, customClientMessage);
    }

    public ResourceNotFoundException() {
        super(DomainErrorType.NOT_FOUND);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(DomainErrorType.NOT_FOUND, cause);
    }
}
