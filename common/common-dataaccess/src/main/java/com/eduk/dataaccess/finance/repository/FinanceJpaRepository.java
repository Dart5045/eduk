package com.eduk.dataaccess.finance.repository;

import com.eduk.dataaccess.finance.entity.FinanceEntity;
import com.eduk.dataaccess.finance.entity.FinanceEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinanceJpaRepository extends JpaRepository<FinanceEntity, FinanceEntityId> {
    Optional<List<FinanceEntity>> findByRestaurantIdAndProductIdIn(UUID restaurantId, List<UUID> productIds);
}
