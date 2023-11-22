package com.online.bakery.order.service.dataaccess.bakery.mapper;

import com.online.bakery.domain.valueobject.BakeryId;
import com.online.bakery.domain.valueobject.Money;
import com.online.bakery.domain.valueobject.ProductId;
import com.online.bakery.order.service.dataaccess.bakery.entity.BakeryEntity;
import com.online.bakery.order.service.dataaccess.bakery.exception.BakeryDataAccessException;
import com.online.bakery.order.service.domain.entity.Bakery;
import com.online.bakery.order.service.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BakeryDataAccessMapper {

    public List<UUID> bakeryToBakeryProducts(Bakery bakery) {
        return bakery.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Bakery bakeryEntityToBakery(List<BakeryEntity> bakeryEntities) {
        BakeryEntity bakeryEntity = bakeryEntities.stream().findFirst().orElseThrow(() ->
                new BakeryDataAccessException("Bakery data could not be found!"));

        List<Product> bakeryProducts = bakeryEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice())))
                .toList();

        return Bakery.builder()
                .bakeryId(new BakeryId(bakeryEntity.getBakeryId()))
                .products(bakeryProducts)
                .build();
    }
}
