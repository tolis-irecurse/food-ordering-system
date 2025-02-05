package com.nttdata.food.ordering.system.order.service.domain.ports;

import com.nttdata.food.ordering.system.order.service.domain.OrderCreateHelper;
import com.nttdata.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderCommand;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderResponse;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    @Autowired
    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {

        var orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order with id {} has been created", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);

        return orderDataMapper.mapOrderToCreateOrderResponse(orderCreatedEvent.getOrder());
    }
}
