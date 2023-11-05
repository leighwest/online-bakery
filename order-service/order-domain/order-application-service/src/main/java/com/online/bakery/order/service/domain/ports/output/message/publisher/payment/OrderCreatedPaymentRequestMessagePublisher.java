package com.online.bakery.order.service.domain.ports.output.message.publisher.payment;

import com.online.bakery.domain.event.publisher.DomainEventPublisher;
import com.online.bakery.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
