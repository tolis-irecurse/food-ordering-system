package com.nttdata.food.ordering.system.common.domain.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;

import java.io.Serializable;


public abstract class DomainErrorCodeException extends RuntimeException implements Serializable {

    //protected static final long SERIAL_VERSION_UID = 1L; //TODO: check where to use

    protected final transient IDomainErrorCode domainErrorCode;

    protected <T extends IDomainErrorCode> DomainErrorCodeException(T domainCode, Object... params) {
        super(String.format(domainCode.getMessage(), params));
        this.domainErrorCode = domainCode;
    }

    protected <T extends IDomainErrorCode> DomainErrorCodeException(Throwable cause, T domainCode, Object... params) {
        super(String.format(domainCode.getMessage(), params), cause);
        this.domainErrorCode = domainCode;
    }

    @SuppressWarnings("unchecked")
    public <T extends IDomainErrorCode> T getDomainErrorCode() {
        return (T) domainErrorCode;
    }
}
