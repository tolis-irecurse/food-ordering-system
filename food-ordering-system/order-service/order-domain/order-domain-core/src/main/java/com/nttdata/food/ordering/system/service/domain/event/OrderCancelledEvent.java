package com.nttdata.food.ordering.system.service.domain.event;

import com.nttdata.food.ordering.system.common.domain.event.DomainEvent;
import com.nttdata.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent implements DomainEvent<Order> {

    private final DomainEventPublisher<OrderCancelledEvent> orderCancelledEventPublisher;

    public OrderCancelledEvent(Order order,
                               ZonedDateTime createdAt,
                               DomainEventPublisher<OrderCancelledEvent> orderCancelledEventPublisher) {
        super(order, createdAt);
        this.orderCancelledEventPublisher = orderCancelledEventPublisher;
    }

    @Override
    public void fire() {
        orderCancelledEventPublisher.publish(this);
    }
}
