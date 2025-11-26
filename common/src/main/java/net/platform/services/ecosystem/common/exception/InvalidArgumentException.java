package net.platform.services.ecosystem.common.exception;

public class InvalidArgumentException extends DomainException {
    public InvalidArgumentException(String customClientMessage, Integer errorCode, Throwable cause) {
        super(DomainErrorType.INVALID_ARGUMENT, customClientMessage, errorCode, cause);
    }

    public InvalidArgumentException(String customClientMessage, Throwable cause) {
        super(DomainErrorType.INVALID_ARGUMENT, customClientMessage, cause);
    }

    public InvalidArgumentException(String customClientMessage, Integer errorCode) {
        super(DomainErrorType.INVALID_ARGUMENT, customClientMessage, errorCode);
    }

    public InvalidArgumentException(String customClientMessage) {
        super(DomainErrorType.INVALID_ARGUMENT, customClientMessage);
    }

    public InvalidArgumentException() {
        super(DomainErrorType.INVALID_ARGUMENT);
    }

    public InvalidArgumentException(Throwable cause) {
        super(DomainErrorType.INVALID_ARGUMENT, cause);
    }
}
