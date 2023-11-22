package com.online.bakery.order.service.dataaccess.customer.repository;

import com.online.bakery.order.service.dataaccess.bakery.entity.BakeryEntity;
import com.online.bakery.order.service.dataaccess.bakery.entity.BakeryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BakeryJpaRepository extends JpaRepository<BakeryEntity, BakeryEntityId> {

    // FIXME: don't need a list of bakery entities, only have one bakery
    Optional<List<BakeryEntity>> findByBakeryIdAndProductIdIn(UUID bakeryId, List<UUID> productIds);

}
