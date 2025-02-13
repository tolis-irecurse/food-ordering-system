package com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;

public enum DataAccessErrorCode implements IDomainErrorCode {

    RESTAURANT_NOT_FOUND("REST-0001", "Restaurant could not be found!");

    private final String code;
    private final String message;

    DataAccessErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
