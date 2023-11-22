package com.online.bakery.order.service.domain.entity;

import com.online.bakery.domain.entity.AggregateRoot;
import com.online.bakery.domain.valueobject.BakeryId;

import java.util.List;

public class Bakery extends AggregateRoot<BakeryId> {
    private final List<Product> products;
    private boolean active;

    private Bakery(Builder builder) {
        super.setId(builder.bakeryId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private BakeryId bakeryId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }


        public Builder bakeryId(BakeryId val) {
            bakeryId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Bakery build() {
            return new Bakery(this);
        }
    }
}
