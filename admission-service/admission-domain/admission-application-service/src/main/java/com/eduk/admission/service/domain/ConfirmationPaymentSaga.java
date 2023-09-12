package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.dto.message.PaymentResponse;
import com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval.ConfirmationPaidFinanceRequestMessagePublisher;
import com.eduk.admission.service.domain.ports.output.repository.ConfirmationRepository;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.EmptyEvent;
import com.eduk.admission.service.domain.exception.ConfirmationNotFoundException;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.saga.SagaStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ConfirmationPaymentSaga implements SagaStep<PaymentResponse, ConfirmationPaidEvent, EmptyEvent> {

    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationRepository confirmationRepository;
    private final ConfirmationPaidFinanceRequestMessagePublisher confirmationPaidFinanceRequestMessagePublisher;

    public ConfirmationPaymentSaga(ConfirmationDomainService confirmationDomainService,
                                   ConfirmationRepository confirmationRepository,
                                   ConfirmationPaidFinanceRequestMessagePublisher confirmationPaidFinanceRequestMessagePublisher) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationRepository = confirmationRepository;
        this.confirmationPaidFinanceRequestMessagePublisher = confirmationPaidFinanceRequestMessagePublisher;
    }

    @Override
    @Transactional
    public ConfirmationPaidEvent process(PaymentResponse paymentResponse) {
        log.info("Completing payment for confirmation with id: {}", paymentResponse.getConfirmationId());
        Confirmation confirmation = findOrder(paymentResponse.getConfirmationId());
        ConfirmationPaidEvent domainEvent = confirmationDomainService
                .payConfirmation(confirmation, confirmationPaidFinanceRequestMessagePublisher);
        confirmationRepository.save(confirmation);
        log.info("Order with id: {} is paid", confirmation.getId().getValue());
        return domainEvent;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(PaymentResponse paymentResponse) {
        log.info("Cancelling confirmation with id: {}", paymentResponse.getConfirmationId());
        Confirmation confirmation = findOrder(paymentResponse.getConfirmationId());
        confirmationDomainService.cancelConfirmation(confirmation, paymentResponse.getFailureMessages());
        confirmationRepository.save(confirmation);
        log.info("Order with id: {} is cancelled", confirmation.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    private Confirmation findOrder(String orderId) {
        Optional<Confirmation> orderResponse = confirmationRepository.findById(new ConfirmationId(UUID.fromString(orderId)));
        if (orderResponse.isEmpty()) {
            log.error("Order with id: {} could not be found!", orderId);
            throw new ConfirmationNotFoundException("Order with id " + orderId + " could not be found!");
        }
        return orderResponse.get();
    }
}
