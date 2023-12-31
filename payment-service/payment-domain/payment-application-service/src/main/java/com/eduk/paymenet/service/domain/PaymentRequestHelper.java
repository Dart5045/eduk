package com.eduk.paymenet.service.domain;

import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.paymenet.service.domain.dto.PaymentRequest;
import com.eduk.paymenet.service.domain.exception.PaymentApplicationServiceException;
import com.eduk.paymenet.service.domain.mapper.PaymentDataMapper;
import com.eduk.paymenet.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.eduk.paymenet.service.domain.ports.output.repository.CreditEntryRepository;
import com.eduk.paymenet.service.domain.ports.output.repository.CreditHistoryRepository;
import com.eduk.paymenet.service.domain.ports.output.repository.PaymentRepository;
import com.eduk.payment.service.domain.PaymentDomainService;
import com.eduk.payment.service.domain.entity.CreditEntry;
import com.eduk.payment.service.domain.entity.CreditHistory;
import com.eduk.payment.service.domain.event.PaymentEvent;
import com.eduk.paymenet.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.eduk.paymenet.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.eduk.payment.service.domain.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;


    private final PaymentCompletedMessagePublisher paymentCompletedEventDomainEventPublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledEventDomainEventPublisher;
    private final PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher;

    //private final ConfirmationOutboxHelper confirmationOutboxHelper;
    //private final PaymentResponseMessagePublisher paymentResponseMessagePublisher;

    public PaymentRequestHelper(PaymentDomainService paymentDomainService,
                                PaymentDataMapper paymentDataMapper,
                                PaymentRepository paymentRepository,
                                CreditEntryRepository creditEntryRepository,
                                CreditHistoryRepository creditHistoryRepository,
                                PaymentCompletedMessagePublisher paymentCompletedEventDomainEventPublisher
            , PaymentCancelledMessagePublisher paymentCancelledEventDomainEventPublisher
            , PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher) {
        this.paymentDomainService = paymentDomainService;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
        this.paymentCompletedEventDomainEventPublisher = paymentCompletedEventDomainEventPublisher;
        this.paymentCancelledEventDomainEventPublisher = paymentCancelledEventDomainEventPublisher;
        //this.confirmationOutboxHelper = confirmationOutboxHelper;
        //this.paymentResponseMessagePublisher = paymentResponseMessagePublisher;
        this.paymentFailedEventDomainEventPublisher = paymentFailedEventDomainEventPublisher;
    }

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        /*if (publishIfOutboxMessageProcessedForPayment(paymentRequest, PaymentStatus.COMPLETED)) {
            log.info("An outbox message with saga id: {} is already saved to database!",
                    paymentRequest.getSagaId());
            return;
        }
        */

        log.info("Received payment complete event for application id: {}", paymentRequest.getApplicationId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getApplicationId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getApplicationId());
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent paymentEvent =
                paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages,
                        paymentCompletedEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);
        return paymentEvent;

        /*confirmationOutboxHelper.saveConfirmationOutboxMessage(paymentDataMapper.paymentEventToConfirmationEventPayload(paymentEvent),
                paymentEvent.getPayment().getPaymentStatus(),
                OutboxStatus.STARTED,
                UUID.fromString(paymentRequest.getSagaId()));*/
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for confirmation id: {}", paymentRequest.getConfirmationId());
        Optional<Payment> paymentResponse = paymentRepository
                .findByConfirmationId(UUID.fromString(paymentRequest.getConfirmationId()));
        if (paymentResponse.isEmpty()) {
            log.error("Payment with confirmation id: {} could not be found!", paymentRequest.getConfirmationId());
            throw new PaymentApplicationServiceException("Payment with confirmation id: " +
                    paymentRequest.getConfirmationId() + " could not be found!");
        }
        Payment payment = paymentResponse.get();
        CreditEntry creditEntry = getCreditEntry(payment.getApplicationId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getApplicationId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService
                .validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages,
                        paymentCancelledEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);
        return paymentEvent;
    }

    private CreditEntry getCreditEntry(ApplicationId applicationId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByApplicationId(applicationId);
        if (creditEntry.isEmpty()) {
            log.error("Could not find credit entry for application: {}", applicationId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit entry for application: " +
                    applicationId.getValue());
        }
        return creditEntry.get();
    }

    private List<CreditHistory> getCreditHistory(ApplicationId applicationId) {
        Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByApplicationID(applicationId.getValue());
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for application: {}", applicationId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit history for customer: " +
                    applicationId.getValue());
        }
        return creditHistories.get();
    }

    private void persistDbObjects(Payment payment,
                                  CreditEntry creditEntry,
                                  List<CreditHistory> creditHistories,
                                  List<String> failureMessages) {
        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }
/*

    private boolean publishIfOutboxMessageProcessedForPayment(PaymentRequest paymentRequest,
                                                              PaymentStatus paymentStatus) {
        Optional<ConfirmationOutboxMessage> confirmationOutboxMessage =
                confirmationOutboxHelper.getCompletedConfirmationOutboxMessageBySagaIdAndPaymentStatus(
                        UUID.fromString(paymentRequest.getSagaId()),
                        paymentStatus);
        if (confirmationOutboxMessage.isPresent()) {
            paymentResponseMessagePublisher.publish(confirmationOutboxMessage.get(), confirmationOutboxHelper::updateOutboxMessage);
            return true;
        }
        return false;
    }*/

}
