package com.online.bakery.order.service.domain;

import com.online.bakery.order.service.domain.entity.Bakery;
import com.online.bakery.order.service.domain.entity.Order;
import com.online.bakery.order.service.domain.entity.Product;
import com.online.bakery.order.service.domain.event.OrderCancelledEvent;
import com.online.bakery.order.service.domain.event.OrderCreatedEvent;
import com.online.bakery.order.service.domain.event.OrderPaidEvent;
import com.online.bakery.order.service.domain.valueobject.OrderDomainService;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final String UTC = "UTC";
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Bakery bakery) {
        setOrderProductInformation(order, bakery);
        order.validateOrder();
        order.initialiseOrder();
        log.info("Order with ID: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancellation initiated for order id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());

    }

    private void setOrderProductInformation(Order order, Bakery bakery) {
        order.getItems().forEach(orderItem -> bakery.getProducts().forEach(bakeryProduct -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(bakeryProduct)) {
                currentProduct.updateWithConfirmedNameAndPrice(bakeryProduct.getName(), bakeryProduct.getPrice());
            }
        }));
    }
}
