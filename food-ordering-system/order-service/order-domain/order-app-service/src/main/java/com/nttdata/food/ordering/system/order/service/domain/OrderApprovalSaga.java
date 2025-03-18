package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.common.domain.event.EmptyEvent;
import com.nttdata.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseMsg;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.saga.SagaStep;
import com.nttdata.food.ordering.system.service.domain.OrderDomainService;
import com.nttdata.food.ordering.system.service.domain.event.OrderCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponseMsg, EmptyEvent, OrderCancelledEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderApprovalSaga(OrderDomainService orderDomainService,
                             OrderSagaHelper orderSagaHelper,
                             OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher, OrderRepository orderRepository) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderCancelledPaymentRequestMessagePublisher = orderCancelledPaymentRequestMessagePublisher;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public EmptyEvent process(RestaurantApprovalResponseMsg restaurantApprovalResponse) {
        log.info("Approving order with id {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id {} is approved", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollback(RestaurantApprovalResponseMsg restaurantApprovalResponse) {
        log.info("Cancelling order with id {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        var cancelOrderPaymentEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages(),
                orderCancelledPaymentRequestMessagePublisher);

        orderSagaHelper.saveOrder(order);
        log.info("Order with id {} is cancelling", order.getId().getValue());
        return cancelOrderPaymentEvent;
    }
}
