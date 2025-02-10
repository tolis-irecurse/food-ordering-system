package com.nttdata.food.ordering.system.service.domain;

import com.nttdata.food.ordering.system.service.domain.event.OrderCancelledEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;
import com.nttdata.food.ordering.system.service.domain.exception.OrderDomainException;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.entity.OrderItem;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.nttdata.food.ordering.system.common.domain.code.DomainErrorCode.RESTAURANT_NOT_ACTIVE;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {

        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id {} has been initialized", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    @Override
    public OrderPaidEvent payOrder(Order order, Restaurant restaurant) {
        order.pay();
        log.info("Order with id {} has been payed", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id {} has been approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is being cancelled for order id {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZONE_UTC));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id {} has been cancelled", order.getId().getValue());
    }

    //<editor-fold private>
    private void setOrderProductInformation(Order order, Restaurant restaurant) {

        order.getItems().stream()
             .map(OrderItem::getProduct)
             .forEach(orderProduct ->
                 restaurant.getProducts().stream()
                         .filter(restaurantProduct -> restaurantProduct.equals(orderProduct))
                         .findFirst()
                         .ifPresent(restaurantProduct ->
                             orderProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice())
                         )
             );

    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException(RESTAURANT_NOT_ACTIVE ,restaurant.getId().getValue());
        }
    }
    //</editor-fold private>
}
