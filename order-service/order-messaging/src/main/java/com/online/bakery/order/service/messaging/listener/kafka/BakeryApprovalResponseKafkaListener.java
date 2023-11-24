package com.online.bakery.order.service.messaging.listener.kafka;

import com.online.bakery.kafka.consumer.KafkaConsumer;
import com.online.bakery.kafka.order.avro.model.BakeryApprovalResponseAvroModel;
import com.online.bakery.kafka.order.avro.model.OrderApprovalStatus;
import com.online.bakery.order.service.domain.ports.input.message.listener.bakeryApproval.BakeryApprovalResponseMessageListener;
import com.online.bakery.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.online.bakery.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
public class BakeryApprovalResponseKafkaListener implements KafkaConsumer<BakeryApprovalResponseAvroModel> {

    private final BakeryApprovalResponseMessageListener bakeryApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public BakeryApprovalResponseKafkaListener(BakeryApprovalResponseMessageListener bakeryApprovalResponseMessageListener,
                                               OrderMessagingDataMapper orderMessagingDataMapper) {
        this.bakeryApprovalResponseMessageListener = bakeryApprovalResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.bakery-approval-consumer-group-id}",
                topics = "${order-service.bakery-approval-response-topic-name}")
    public void receive(@Payload List<BakeryApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of bakery approval responses received with keys {}, partitions {}, and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(bakeryApprovalResponseAvroModel -> {
            if (OrderApprovalStatus.APPROVED == bakeryApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing approved order for order id: {}",
                        bakeryApprovalResponseAvroModel.getId());
                bakeryApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(bakeryApprovalResponseAvroModel));
            }                  else if (OrderApprovalStatus.REJECTED == bakeryApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing rejected order for order id: {}, with failure messages: {}",
                        bakeryApprovalResponseAvroModel.getOrderId(),
                        String.join(FAILURE_MESSAGE_DELIMITER, bakeryApprovalResponseAvroModel.getFailureMessages()));
                bakeryApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(bakeryApprovalResponseAvroModel));
            }
        });

    }
}
