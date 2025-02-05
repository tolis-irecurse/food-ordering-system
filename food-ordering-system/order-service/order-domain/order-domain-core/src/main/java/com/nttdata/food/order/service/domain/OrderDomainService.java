package com.nttdata.food.order.service.domain;

import com.nttdata.food.order.service.domain.entity.Order;
import com.nttdata.food.order.service.domain.entity.Restaurant;
import com.nttdata.food.order.service.domain.event.OrderCancelledEvent;
import com.nttdata.food.order.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order, Restaurant restaurant);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
}
