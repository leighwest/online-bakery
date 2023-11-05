package com.online.bakery.order.service.domain.ports.output.repository;

import com.online.bakery.order.service.domain.entity.Bakery;

import java.util.Optional;

public interface BakeryRepository {

    Bakery fetchBakeryInformation();
}
