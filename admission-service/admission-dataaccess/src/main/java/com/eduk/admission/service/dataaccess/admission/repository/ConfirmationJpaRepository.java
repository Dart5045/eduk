package com.eduk.admission.service.dataaccess.admission.repository;

import com.eduk.admission.service.dataaccess.admission.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationJpaRepository extends JpaRepository<ConfirmationEntity, UUID> {
    Optional<ConfirmationEntity> findByTrackingId(UUID trackingId);
}
