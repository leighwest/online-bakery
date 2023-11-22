package com.online.bakery.order.service.dataaccess.bakery.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BakeryEntityId implements Serializable {

    private UUID bakeryId;
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BakeryEntityId that = (BakeryEntityId) o;
        return Objects.equals(bakeryId, that.bakeryId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bakeryId, productId);
    }
}
