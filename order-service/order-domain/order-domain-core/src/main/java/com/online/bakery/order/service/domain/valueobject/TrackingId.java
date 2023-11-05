package com.online.bakery.order.service.domain.valueobject;

import com.online.bakery.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
