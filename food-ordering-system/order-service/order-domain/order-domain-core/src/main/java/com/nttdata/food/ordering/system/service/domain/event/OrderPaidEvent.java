package com.nttdata.food.ordering.system.service.domain.event;

import com.nttdata.food.ordering.system.common.domain.event.DomainEvent;
import com.nttdata.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent implements DomainEvent<Order> {

    private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;

    public OrderPaidEvent(Order order,
                          ZonedDateTime createdAt,
                          DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderPaidEventDomainEventPublisher = orderPaidEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderPaidEventDomainEventPublisher.publish(this);
    }
}
