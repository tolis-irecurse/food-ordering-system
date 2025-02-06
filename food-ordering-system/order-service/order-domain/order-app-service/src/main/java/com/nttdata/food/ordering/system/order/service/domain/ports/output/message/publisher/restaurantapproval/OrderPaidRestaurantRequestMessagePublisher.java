package com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.nttdata.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
