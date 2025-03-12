package com.nttdata.food.ordering.system.restaurant.service.domain.exception;


import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {

    public RestaurantApplicationServiceException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public RestaurantApplicationServiceException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }
}
