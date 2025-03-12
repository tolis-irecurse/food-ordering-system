package com.nttdata.food.ordering.system.payment.service.domain.exception;

import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;

public enum PaymentErrorCode implements IDomainErrorCode {

    PAYMENT_ORDER_ID_NOT_FOUND("PAY-0001", "Payment with order id %s could not be found!"),
    NO_CREDIT_ENTRY_FOR_CUSTOMER("PAY-0002", "Could not find credit entry for customer %s!"),
    NO_CREDIT_HISTORY_FOR_CUSTOMER("PAY-0003", "Could not find credit history for customer %s!");

    private final String code;
    private final String message;

    PaymentErrorCode(String code, String message) {
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
