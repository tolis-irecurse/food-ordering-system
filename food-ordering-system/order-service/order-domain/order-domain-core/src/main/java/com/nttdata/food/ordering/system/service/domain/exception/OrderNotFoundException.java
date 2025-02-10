package com.nttdata.food.ordering.system.service.domain.exception;

import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

import static com.nttdata.food.ordering.system.common.domain.code.DomainErrorCode.ORDER_NOT_FOUND;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String id) {
        super(ORDER_NOT_FOUND, id);
    }

    public OrderNotFoundException(Throwable cause, String id) {
        super(cause, ORDER_NOT_FOUND, id);
    }
}
