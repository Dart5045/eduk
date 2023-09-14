package com.eduk.admission.service.dataaccess.outbox.payment.adapter;

import com.eduk.admission.service.dataaccess.outbox.payment.exception.PaymentOutboxNotFoundException;
import com.eduk.admission.service.dataaccess.outbox.payment.mapper.PaymentOutboxDataAccessMapper;
import com.eduk.admission.service.dataaccess.outbox.payment.repository.PaymentOutboxJpaRepository;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.admission.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

    private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;
    private final PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper;

    public PaymentOutboxRepositoryImpl(PaymentOutboxJpaRepository paymentOutboxJpaRepository,
                                       PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper) {
        this.paymentOutboxJpaRepository = paymentOutboxJpaRepository;
        this.paymentOutboxDataAccessMapper = paymentOutboxDataAccessMapper;
    }

    @Override
    public ConfirmationPaymentOutboxMessage save(ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage) {
        return paymentOutboxDataAccessMapper
                .paymentOutboxEntityToConfirmationPaymentOutboxMessage(paymentOutboxJpaRepository
                        .save(paymentOutboxDataAccessMapper
                                .confirmationPaymentOutboxMessageToOutboxEntity(confirmationPaymentOutboxMessage)));
    }

    @Override
    public Optional<List<ConfirmationPaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String sagaType,
                                                                                                   OutboxStatus outboxStatus,
                                                                                                   SagaStatus... sagaStatus) {
        return Optional.of(paymentOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(sagaType,
                        outboxStatus,
                        Arrays.asList(sagaStatus))
                .orElseThrow(() -> new PaymentOutboxNotFoundException("Payment outbox object " +
                        "could not be found for saga type " + sagaType))
                .stream()
                .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToConfirmationPaymentOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ConfirmationPaymentOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type,
                                                                                UUID sagaId,
                                                                                SagaStatus... sagaStatus) {

        return paymentOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type,
                        sagaId,
                        Arrays.asList(sagaStatus))
                .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToConfirmationPaymentOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        paymentOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus,
                Arrays.asList(sagaStatus));
    }
}
