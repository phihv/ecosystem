package net.platform.services.ecosystem.common.exception;

public abstract class DomainException extends RuntimeException {
    protected final DomainErrorType errorType;
    protected final Integer errorCode;
    protected final String message;

    public DomainException(DomainErrorType errorType, String customClientMessage, Integer errorCode, Throwable cause) {
        super(customClientMessage, cause);
        this.errorType = errorType;
        this.message = customClientMessage;
        this.errorCode = errorCode;
    }

    public DomainException(DomainErrorType errorType, String customClientMessage, Integer errorCode) {
        super(customClientMessage);
        this.errorType = errorType;
        this.message = customClientMessage;
        this.errorCode = errorCode;
    }

    public DomainException(DomainErrorType errorType, String customClientMessage, Throwable cause) {
        this(errorType, customClientMessage, (Integer)null, cause);
    }

    public DomainException(DomainErrorType errorType, String customClientMessage) {
        this(errorType, customClientMessage, (Integer)null);
    }

    public DomainException(DomainErrorType errorType) {
        this(errorType, errorType.getDefaultMessage(), (Integer)null);
    }

    public DomainException(DomainErrorType errorType, Throwable cause) {
        this(errorType, errorType.getDefaultMessage(), (Integer)null, cause);
    }

    public String businessErrorCode() {
        return this.errorType.getType();
    }

    public DomainErrorType errorType() {
        return this.errorType;
    }

    public String message() {
        return this.message;
    }

    public Integer errorCode() {
        return this.errorCode;
    }
}
