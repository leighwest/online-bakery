package com.online.bakery.order.service.domain.valueobject;

import com.online.bakery.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {

    public OrderItemId(Long value) {
        super(value);
    }
}
