package com.nttdata.food.ordering.system.restaurant.service.domain.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class RestaurantDomainException extends DomainException {

    public RestaurantDomainException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public RestaurantDomainException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }
}
