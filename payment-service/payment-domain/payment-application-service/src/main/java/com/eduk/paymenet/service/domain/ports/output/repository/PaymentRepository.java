package com.eduk.paymenet.service.domain.ports.output.repository;

import com.eduk.payment.service.domain.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByConfirmationID(UUID confirmationId);
}
