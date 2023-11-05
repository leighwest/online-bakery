package com.online.bakery.order.service.domain.ports.output.message.publisher.payment;

import com.online.bakery.domain.event.publisher.DomainEventPublisher;
import com.online.bakery.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
