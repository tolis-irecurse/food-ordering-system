package com.nttdata.food.ordering.system.service.domain.event;

import com.nttdata.food.ordering.system.service.domain.entity.Order;
import com.nttdata.food.ordering.system.domain.event.DomainEvent;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent implements DomainEvent<Order> {
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
