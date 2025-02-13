package com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainErrorCodeException;

public class RestaurantDataAccessException extends DomainErrorCodeException {

    public <T extends IDomainErrorCode> RestaurantDataAccessException(T domainCode, Object... params) {
        super(domainCode, params);
    }

    public <T extends IDomainErrorCode> RestaurantDataAccessException(Throwable cause, T domainCode, Object... params) {
        super(cause, domainCode, params);
    }
}
