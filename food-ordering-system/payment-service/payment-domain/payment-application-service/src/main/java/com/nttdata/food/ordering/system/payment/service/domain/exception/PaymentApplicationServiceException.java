package com.nttdata.food.ordering.system.payment.service.domain.exception;


import com.nttdata.food.ordering.system.common.domain.code.IDomainErrorCode;
import com.nttdata.food.ordering.system.common.domain.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException {

    public PaymentApplicationServiceException(IDomainErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public PaymentApplicationServiceException(Throwable cause, IDomainErrorCode domainErrorCode, Object... params) {
        super(cause, domainErrorCode, params);
    }
}
