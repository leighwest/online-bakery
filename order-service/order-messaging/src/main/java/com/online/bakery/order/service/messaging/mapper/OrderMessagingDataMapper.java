package com.online.bakery.order.service.messaging.mapper;

import com.online.bakery.domain.valueobject.OrderApprovalStatus;
import com.online.bakery.domain.valueobject.PaymentStatus;
import com.online.bakery.kafka.order.avro.model.*;
import com.online.bakery.order.service.domain.dto.message.BakeryApprovalResponse;
import com.online.bakery.order.service.domain.dto.message.PaymentResponse;
import com.online.bakery.order.service.domain.entity.Order;
import com.online.bakery.order.service.domain.event.OrderCancelledEvent;
import com.online.bakery.order.service.domain.event.OrderCreatedEvent;
import com.online.bakery.order.service.domain.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public BakeryApprovalRequestAvroModel orderPaidEventToBakeryApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent) {
        Order order = orderPaidEvent.getOrder();
        return BakeryApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setBakeryId(order.getId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setBakeryOrderStatus(BakeryOrderStatus.valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem ->
                        Product.newBuilder()
                                .setId(orderItem.getProduct().getId().getValue().toString())
                                .setQuantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setBakeryOrderStatus(BakeryOrderStatus.PAID)
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {

        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public BakeryApprovalResponse
    approvalResponseAvroModelToApprovalResponse(BakeryApprovalResponseAvroModel bakeryApprovalResponseAvroModel) {
        return BakeryApprovalResponse.builder()
                .id(bakeryApprovalResponseAvroModel.getId())
                .sagaId(bakeryApprovalResponseAvroModel.getSagaId())
                .bakeryId(bakeryApprovalResponseAvroModel.getBakeryId())
                .orderId(bakeryApprovalResponseAvroModel.getOrderId())
                .createdAt(bakeryApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(OrderApprovalStatus.valueOf(
                        bakeryApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(bakeryApprovalResponseAvroModel.getFailureMessages())
                .build();
    }
}
