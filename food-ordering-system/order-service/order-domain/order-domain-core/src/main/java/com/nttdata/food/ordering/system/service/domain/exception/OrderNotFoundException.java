package com.nttdata.food.ordering.system.service.domain.exception;

import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
