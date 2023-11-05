package com.online.bakery.domain.event.publisher;

import com.online.bakery.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
