package com.nttdata.food.ordering.system.restaurant.service.domain.ports.output.message.publisher;

import com.nttdata.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
