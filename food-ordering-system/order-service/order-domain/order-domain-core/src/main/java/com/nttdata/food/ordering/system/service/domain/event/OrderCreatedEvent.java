package com.nttdata.food.ordering.system.service.domain.event;

import com.nttdata.food.ordering.system.common.domain.event.DomainEvent;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent implements DomainEvent<Order> {
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
