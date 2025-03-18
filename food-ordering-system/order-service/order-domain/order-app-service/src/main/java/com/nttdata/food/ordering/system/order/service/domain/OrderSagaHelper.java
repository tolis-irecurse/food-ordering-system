package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.common.domain.valueobject.OrderId;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.service.domain.exception.OrderNotFoundException;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    public OrderSagaHelper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    Order findOrder(String orderId) {
        return
            orderRepository.findById(new OrderId(UUID.fromString(orderId)))
                    .orElseThrow( () -> {
                        log.error("Order with id {} not found", orderId);
                        return new OrderNotFoundException(orderId);
                    });
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }

}
