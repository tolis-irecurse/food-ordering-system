package com.nttdata.food.ordering.system.service.domain;

import com.nttdata.food.ordering.system.service.domain.entity.Order;
import com.nttdata.food.ordering.system.service.domain.entity.Restaurant;
import com.nttdata.food.ordering.system.service.domain.event.OrderCancelledEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order, Restaurant restaurant);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
