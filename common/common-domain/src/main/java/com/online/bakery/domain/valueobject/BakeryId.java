package com.online.bakery.domain.valueobject;

import java.util.UUID;

public class BakeryId extends BaseId<UUID>{

    public BakeryId(UUID value) {
        super(value);
    }
}
