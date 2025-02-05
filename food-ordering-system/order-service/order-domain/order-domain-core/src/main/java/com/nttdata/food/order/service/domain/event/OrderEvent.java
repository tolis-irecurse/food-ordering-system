package com.nttdata.food.order.service.domain.event;

import com.nttdata.food.order.service.domain.entity.Order;
import com.nttdata.food.ordering.system.domain.event.DomainEvent;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }
}
