package com.online.bakery.order.service.domain.ports.input.message.listener.bakeryApproval;

import com.online.bakery.order.service.domain.dto.message.BakeryApprovalResponse;

public interface BakeryApprovalResponseMessageListener {

    void orderApproved(BakeryApprovalResponse bakeryApprovalResponse);

    void orderRejected(BakeryApprovalResponse bakeryApprovalResponse);
}
