package com.nttdata.food.ordering.system.payment.service.domain.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.common.domain.valueobject.Money;
import com.nttdata.food.ordering.system.common.domain.valueobject.OrderId;
import com.nttdata.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.nttdata.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
