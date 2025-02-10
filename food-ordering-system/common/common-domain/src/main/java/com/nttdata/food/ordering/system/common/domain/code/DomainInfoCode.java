package com.nttdata.food.ordering.system.common.domain.code;

public enum DomainInfoCode implements IDomainInfoCode {

    ORDER_CREATED_SUCCESSFULLY("CREATE-0001", "Order created successfully");

    private final String code;
    private final String message;

    DomainInfoCode(String code, String message) {
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
