package com.online.bakery.order.service.dataaccess.bakery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BakeryEntityId.class)
@Table(name = "order_bakery_m_view", schema = "bakery")
@Entity
public class BakeryEntity {

    @Id
    private UUID bakeryId;

    @Id
    private UUID productId;

    private String bakeryName;

    private String productName;

    private BigDecimal productPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BakeryEntity that = (BakeryEntity) o;
        return Objects.equals(bakeryId, that.bakeryId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bakeryId, productId);
    }
}
