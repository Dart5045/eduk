package com.eduk.payment.service.dataaccess.payment.adapter;

import com.eduk.paymenet.service.domain.ports.output.repository.PaymentRepository;
import com.eduk.payment.service.dataaccess.payment.mapper.PaymentDataAccessMapper;
import com.eduk.payment.service.dataaccess.payment.repository.PaymentJpaRepository;
import com.eduk.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository,
                                 PaymentDataAccessMapper paymentDataAccessMapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentDataAccessMapper = paymentDataAccessMapper;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper
                .paymentEntityToPayment(paymentJpaRepository
                        .save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }


    @Override
    public Optional<Payment> findByConfirmationId(UUID confirmationId) {
        return paymentJpaRepository.findByConfirmationId(confirmationId)
                .map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}
