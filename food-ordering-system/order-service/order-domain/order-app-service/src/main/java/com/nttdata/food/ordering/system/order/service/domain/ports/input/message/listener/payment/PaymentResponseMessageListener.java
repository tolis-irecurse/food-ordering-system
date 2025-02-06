package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.nttdata.food.ordering.system.order.service.domain.dto.message.PaymentResponseMsg;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponseMsg paymentResponse);

    void paymentCancelled(PaymentResponseMsg paymentResponse);
}
