package com.online.bakery.order.service.domain.entity;

import com.online.bakery.domain.entity.BaseEntity;
import com.online.bakery.domain.valueobject.ProductId;
import com.online.bakery.domain.valueobject.Money;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
