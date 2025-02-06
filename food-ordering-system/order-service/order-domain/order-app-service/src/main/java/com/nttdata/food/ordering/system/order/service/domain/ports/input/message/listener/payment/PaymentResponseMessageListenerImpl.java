package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.nttdata.food.ordering.system.order.service.domain.dto.message.PaymentResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    @Override
    public void paymentCompleted(PaymentResponseMsg paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponseMsg paymentResponse) {

    }
}
