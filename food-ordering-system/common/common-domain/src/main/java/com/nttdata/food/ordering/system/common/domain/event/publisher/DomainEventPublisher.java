package com.nttdata.food.ordering.system.common.domain.event.publisher;

import com.nttdata.food.ordering.system.common.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
