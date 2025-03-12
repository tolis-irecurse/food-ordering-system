package com.nttdata.food.ordering.system.payment.service.domain.exception;


import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {

    public PaymentDomainException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public PaymentDomainException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }

  /*  public PaymentDomainException(String message) {
        super(message);
    }

    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }*/
}
