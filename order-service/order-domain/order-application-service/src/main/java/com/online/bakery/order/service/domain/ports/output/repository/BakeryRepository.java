package com.online.bakery.order.service.domain.ports.output.repository;

import com.online.bakery.order.service.domain.entity.Bakery;

import java.util.Optional;

public interface BakeryRepository {

//    Bakery fetchBakeryInformation();

    // FIXME: shouldn't need an optional - I know I have a bakery
    Optional<Bakery> findBakeryInformation(Bakery bakery);
}
