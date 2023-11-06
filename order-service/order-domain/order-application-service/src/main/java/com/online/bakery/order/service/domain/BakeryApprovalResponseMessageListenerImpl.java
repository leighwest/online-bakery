package com.online.bakery.order.service.domain;

import com.online.bakery.order.service.domain.dto.message.BakeryApprovalResponse;
import com.online.bakery.order.service.domain.ports.input.message.listener.bakeryApproval.BakeryApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class BakeryApprovalResponseMessageListenerImpl implements BakeryApprovalResponseMessageListener {
    @Override
    public void orderApproved(BakeryApprovalResponse bakeryApprovalResponse) {

    }

    @Override
    public void orderRejected(BakeryApprovalResponse bakeryApprovalResponse) {

    }
}
