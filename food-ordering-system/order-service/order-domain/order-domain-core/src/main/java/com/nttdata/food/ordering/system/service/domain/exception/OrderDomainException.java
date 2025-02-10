package com.nttdata.food.ordering.system.service.domain.exception;

import com.nttdata.food.ordering.system.common.domain.code.DomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class OrderDomainException extends DomainException {

    public OrderDomainException(DomainErrorCode domainErrorCode, Object... params) {
        super(domainErrorCode, params);
    }

    public OrderDomainException(Throwable cause, DomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }
}
