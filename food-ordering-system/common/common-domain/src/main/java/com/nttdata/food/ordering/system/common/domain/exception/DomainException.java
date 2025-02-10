package com.nttdata.food.ordering.system.common.domain.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;

public class DomainException extends DomainErrorCodeException {

    public DomainException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public DomainException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }
}
