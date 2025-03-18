package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.common.domain.event.EmptyEvent;
import com.nttdata.food.ordering.system.order.service.domain.dto.message.PaymentResponseMsg;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.nttdata.food.ordering.system.saga.SagaStep;
import com.nttdata.food.ordering.system.service.domain.OrderDomainService;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponseMsg, OrderPaidEvent, EmptyEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;

    @Autowired
    public OrderPaymentSaga(OrderDomainService orderDomainService,
                            OrderSagaHelper orderSagaHelper,
                            OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderPaidRestaurantRequestMessagePublisher = orderPaidRestaurantRequestMessagePublisher;
    }

    @Override
    @Transactional
    public OrderPaidEvent process(PaymentResponseMsg paymentResponse) {
        log.info("Completing payment for order with id {}", paymentResponse.getOrderId());
        var order = orderSagaHelper.findOrder(paymentResponse.getOrderId());

        var orderPaidEvent = orderDomainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher);

        orderSagaHelper.saveOrder(order);
        log.info("Order with id {} is paid", order.getId().getValue());

        return orderPaidEvent;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(PaymentResponseMsg paymentResponse) {
        log.info("Cancelling order with id {}", paymentResponse.getOrderId());

        var order = orderSagaHelper.findOrder(paymentResponse.getOrderId());
        orderDomainService.cancelOrder(order, paymentResponse.getFailureMessages());

        orderSagaHelper.saveOrder(order);
        log.info("Order with id {} has been canceled", order.getId().getValue());

        return EmptyEvent.INSTANCE;
    }
}
