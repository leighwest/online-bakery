package com.online.bakery.order.service.dataaccess.bakery.adapter;

import com.online.bakery.order.service.dataaccess.bakery.entity.BakeryEntity;
import com.online.bakery.order.service.dataaccess.bakery.mapper.BakeryDataAccessMapper;
import com.online.bakery.order.service.dataaccess.customer.repository.BakeryJpaRepository;
import com.online.bakery.order.service.domain.entity.Bakery;
import com.online.bakery.order.service.domain.ports.output.repository.BakeryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BakeryRepositoryImpl implements BakeryRepository {

    private final BakeryJpaRepository bakeryJpaRepository;
    private final BakeryDataAccessMapper bakeryDataAccessMapper;

    public BakeryRepositoryImpl(BakeryJpaRepository bakeryJpaRepository, BakeryDataAccessMapper bakeryDataAccessMapper) {
        this.bakeryJpaRepository = bakeryJpaRepository;
        this.bakeryDataAccessMapper = bakeryDataAccessMapper;
    }

    // FIXME: don't need a list of bakery entities, only have one bakery.
    @Override
    public Optional<Bakery> findBakeryInformation(Bakery bakery) {
        List<UUID> bakeryProducts = bakeryDataAccessMapper.bakeryToBakeryProducts(bakery);

        Optional<List<BakeryEntity>> bakeryEntities = bakeryJpaRepository.findByBakeryIdAndProductIdIn(bakery.getId().getValue(),
                bakeryProducts);

        return bakeryEntities.map(bakeryDataAccessMapper::bakeryEntityToBakery);
    }
}
