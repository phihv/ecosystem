package net.platform.services.ecosystem.common.exception;


public class DuplicateResourceException extends DomainException {
    public DuplicateResourceException(String customClientMessage, Integer errorCode, Throwable cause) {
        super(DomainErrorType.DUPLICATE_RESOURCE, customClientMessage, errorCode, cause);
    }

    public DuplicateResourceException(String customClientMessage, Throwable cause) {
        super(DomainErrorType.DUPLICATE_RESOURCE, customClientMessage, cause);
    }

    public DuplicateResourceException(String customClientMessage, Integer errorCode) {
        super(DomainErrorType.DUPLICATE_RESOURCE, customClientMessage, errorCode);
    }

    public DuplicateResourceException(String customClientMessage) {
        super(DomainErrorType.DUPLICATE_RESOURCE, customClientMessage);
    }

    public DuplicateResourceException() {
        super(DomainErrorType.DUPLICATE_RESOURCE);
    }

    public DuplicateResourceException(Throwable cause) {
        super(DomainErrorType.DUPLICATE_RESOURCE, cause);
    }
}
