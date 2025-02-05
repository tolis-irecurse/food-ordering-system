package com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.nttdata.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
