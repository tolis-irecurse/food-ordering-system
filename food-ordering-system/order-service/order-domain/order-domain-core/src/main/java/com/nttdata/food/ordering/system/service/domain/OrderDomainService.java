package com.nttdata.food.ordering.system.service.domain;

import com.nttdata.food.ordering.system.service.domain.event.OrderCancelledEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order, Restaurant restaurant);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
