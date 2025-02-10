package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.nttdata.food.ordering.system.service.domain.OrderDomainService;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.ordering.system.service.domain.exception.OrderDomainException;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.nttdata.food.ordering.system.common.domain.code.DomainErrorCode.*;

@Slf4j
@Component
public class OrderCreateHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommandDTO createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());

        var restaurant = checkRestaurant(createOrderCommand);
        var order = orderDataMapper.mapCreateOrderCommandDTOToOrder(createOrderCommand);
        var orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        saveOrder(order);
        log.info("Order with id {} has been created", orderCreatedEvent.getOrder().getId().getValue() );

        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommandDTO createOrderCommand) {
        Restaurant cmdRestaurant = orderDataMapper.mapCreateOrderCommandDTOToRestaurant(createOrderCommand);

        return restaurantRepository.findRestaurantInformation(cmdRestaurant.getId())
                .orElseThrow( () -> {
                    //log.warn("Could not find restaurant with id {}", createOrderCommand.getRestaurantId(); //TODO Log through controller advice
                    return new OrderDomainException(RESTAURANT_NOT_FOUND, createOrderCommand.getRestaurantId());
                });
    }

    private void checkCustomer(UUID customerId) {

        customerRepository.findCustomer(customerId)
                .orElseThrow( () -> {
                    //log.warn("Customer with id {} not found", customerId); //TODO Log through controller advice
                    return new OrderDomainException(CUSTOMER_NOT_FOUND, customerId);
                });

    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            //log.error("Could not save order with id {}", order); //TODO Log through controller advice
            throw new OrderDomainException(ORDER_COULD_NOT_SAVE, order.getId());
        }
        log.info("Saved order with id {}", orderResult.getId().getValue());
        return orderResult;
    }
}
