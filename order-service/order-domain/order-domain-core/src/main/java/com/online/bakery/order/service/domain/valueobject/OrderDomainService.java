package com.online.bakery.order.service.domain.valueobject;

import com.online.bakery.order.service.domain.entity.Bakery;
import com.online.bakery.order.service.domain.entity.Order;
import com.online.bakery.order.service.domain.event.OrderCancelledEvent;
import com.online.bakery.order.service.domain.event.OrderCreatedEvent;
import com.online.bakery.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Bakery bakery);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
