package com.online.bakery.order.service.domain.entity;

import com.online.bakery.domain.entity.AggregateRoot;
import com.online.bakery.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer() {
    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
