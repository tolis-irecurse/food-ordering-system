package com.nttdata.food.order.service.domain.event;

import com.nttdata.food.order.service.domain.entity.Order;
import com.nttdata.food.ordering.system.domain.event.DomainEvent;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent implements DomainEvent<Order> {
    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
