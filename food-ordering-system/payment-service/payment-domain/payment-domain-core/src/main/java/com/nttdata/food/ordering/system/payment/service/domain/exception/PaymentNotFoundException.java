package com.nttdata.food.ordering.system.payment.service.domain.exception;


import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class PaymentNotFoundException extends DomainException {

    public PaymentNotFoundException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public PaymentNotFoundException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }

    /*public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }*/
}
