package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.nttdata.food.ordering.system.order.service.domain.OrderPaymentSaga;
import com.nttdata.food.ordering.system.order.service.domain.dto.message.PaymentResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.nttdata.food.ordering.system.service.domain.model.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    private final OrderPaymentSaga orderPaymentSaga;

    @Autowired
    public PaymentResponseMessageListenerImpl(OrderPaymentSaga orderPaymentSaga) {
        this.orderPaymentSaga = orderPaymentSaga;
    }

    @Override
    public void paymentCompleted(PaymentResponseMsg paymentResponse) {
        var orderPaidEvent = orderPaymentSaga.process(paymentResponse);
        log.info("Publishing OrderPaidEvent for order id {}", paymentResponse.getOrderId());
        orderPaidEvent.fire();
    }

    @Override
    public void paymentCancelled(PaymentResponseMsg paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info(
            "Order is rolled back for order id {} with failure messages {}",
            paymentResponse.getOrderId(),
            String.join(FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages())
        );
    }
}
