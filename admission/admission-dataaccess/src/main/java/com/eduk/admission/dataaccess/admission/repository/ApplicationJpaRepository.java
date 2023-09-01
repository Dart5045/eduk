package com.eduk.admission.dataaccess.admission.repository;

import com.eduk.admission.dataaccess.admission.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationJpaRepository extends JpaRepository<ApplicationEntity, UUID> {
    Optional<ApplicationEntity> findByTrackingId(UUID trackingId);
}
