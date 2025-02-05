package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.nttdata.food.ordering.system.order.service.domain.payload.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
