package com.eduk.finance.service.dataaccess.finance.repository;

import com.eduk.finance.service.dataaccess.finance.entity.ConfirmationApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmationApprovalJpaRepository extends JpaRepository<ConfirmationApprovalEntity, UUID> {


}
