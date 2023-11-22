package com.online.bakery.order.service.messaging.publisher.kafka;

import com.online.bakery.kafka.order.avro.model.BakeryApprovalRequestAvroModel;
import com.online.bakery.kafka.producer.service.KafkaProducer;
import com.online.bakery.order.service.domain.config.OrderServiceConfigData;
import com.online.bakery.order.service.domain.event.OrderPaidEvent;
import com.online.bakery.order.service.domain.ports.output.message.publisher.bakeryapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.online.bakery.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;

    private final OrderServiceConfigData orderServiceConfigData;

    private final KafkaProducer<String, BakeryApprovalRequestAvroModel> kafkaProducer;

    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                         OrderServiceConfigData orderServiceConfigData,
                                         KafkaProducer<String, BakeryApprovalRequestAvroModel> kafkaProducer,
                                         OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();

        try {
            BakeryApprovalRequestAvroModel bakeryApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToBakeryApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getBakeryApprovalRequestTopicName(),
                    orderId,
                    bakeryApprovalRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getBakeryApprovalRequestTopicName(),
                                    bakeryApprovalRequestAvroModel,
                                    orderId,
                                    "BakeryApprovalRequestAvroModel"));

            log.info("BakeryApprovalRequestAvroModel sent to kafka for orderId: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending BakeryApprovalRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }

    }
}
