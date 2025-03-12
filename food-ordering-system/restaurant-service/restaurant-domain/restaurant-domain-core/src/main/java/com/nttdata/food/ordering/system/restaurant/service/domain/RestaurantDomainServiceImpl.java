package com.nttdata.food.ordering.system.restaurant.service.domain;

import com.nttdata.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.nttdata.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import com.nttdata.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.nttdata.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.nttdata.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {

    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    @Override
    public OrderApprovalEvent validateOrder(Restaurant restaurant,
                                            List<String> failureMessages,
                                            DomainEventPublisher<OrderApprovedEvent>
                                                    orderApprovedEventDomainEventPublisher,
                                            DomainEventPublisher<OrderRejectedEvent>
                                                    orderRejectedEventDomainEventPublisher) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order with id: {}", restaurant.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(),
                    restaurant.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZONE_UTC));
        } else {
            log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
            return new OrderRejectedEvent(restaurant.getOrderApproval(),
                    restaurant.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZONE_UTC));
        }
    }
}
