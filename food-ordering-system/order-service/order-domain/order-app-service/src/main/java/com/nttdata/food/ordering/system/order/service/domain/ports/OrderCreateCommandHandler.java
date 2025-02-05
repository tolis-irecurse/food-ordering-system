package com.nttdata.food.ordering.system.order.service.domain.ports;

import com.nttdata.food.ordering.system.order.service.domain.ApplicationDomainEventPublisher;
import com.nttdata.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderCommand;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderResponse;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.nttdata.food.ordering.system.service.domain.OrderDomainService;
import com.nttdata.food.ordering.system.service.domain.entity.Order;
import com.nttdata.food.ordering.system.service.domain.entity.Restaurant;
import com.nttdata.food.ordering.system.service.domain.exception.OrderDomainException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;
    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    @Autowired
    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     OrderRepository orderRepository,
                                     CustomerRepository customerRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper,
                                     ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());

        var restaurant = checkRestaurant(createOrderCommand);
        var order = orderDataMapper.mapCreateOrderCommandToOrder(createOrderCommand);
        var orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        var orderResult = saveOrder(order);
        log.info("Order with id {} has been created", orderResult.getId().getValue());

        applicationDomainEventPublisher.publish(orderCreatedEvent);
        return orderDataMapper.mapOrderToCreateOrderResponse(orderResult);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant cmdRestaurant = orderDataMapper.mapCreateOrderCommandToRestaurant(createOrderCommand);

        return restaurantRepository.findRestaurantInformation(cmdRestaurant.getId())
                .orElseThrow( () -> {
                   log.warn("Could not find restaurant with id {}", createOrderCommand.getRestaurantId());
                   return new OrderDomainException("Could not find restaurant with id " + createOrderCommand.getRestaurantId());
                });
    }

    private void checkCustomer(UUID customerId) {

        customerRepository.findCustomer(customerId)
                .orElseThrow( () -> {
                    log.warn("Customer with id {} not found", customerId);
                    return new OrderDomainException("Could not find customer with id " + customerId);
                });

    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order with id {}", order);
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Saved order with id {}", orderResult.getId().getValue());
        return orderResult;
    }
}
