package com.online.bakery.order.service.domain.ports.output.message.publisher.bakeryapproval;

import com.online.bakery.domain.event.publisher.DomainEventPublisher;
import com.online.bakery.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
