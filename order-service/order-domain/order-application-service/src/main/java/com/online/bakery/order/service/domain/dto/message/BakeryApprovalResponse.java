package com.online.bakery.order.service.domain.dto.message;

import com.online.bakery.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BakeryApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String bakeryId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
}
