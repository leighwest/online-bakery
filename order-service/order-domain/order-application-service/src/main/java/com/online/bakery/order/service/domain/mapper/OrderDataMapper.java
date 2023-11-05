package com.online.bakery.order.service.domain.mapper;

import com.online.bakery.domain.valueobject.CustomerId;
import com.online.bakery.domain.valueobject.Money;
import com.online.bakery.domain.valueobject.ProductId;
import com.online.bakery.order.service.domain.dto.create.CreateOrderCommand;
import com.online.bakery.order.service.domain.dto.create.CreateOrderResponse;
import com.online.bakery.order.service.domain.dto.create.OrderAddress;
import com.online.bakery.order.service.domain.entity.Order;
import com.online.bakery.order.service.domain.entity.OrderItem;
import com.online.bakery.order.service.domain.entity.Product;
import com.online.bakery.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();

    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<com.online.bakery.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.getProductId())))
                        .price(new Money(orderItem.getPrice()))
                        .quantity(orderItem.getQuantity())
                        .subTotal(new Money(orderItem.getSubTotal()))
                        .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostCode(),
                orderAddress.getCity()
        );
    }

}
