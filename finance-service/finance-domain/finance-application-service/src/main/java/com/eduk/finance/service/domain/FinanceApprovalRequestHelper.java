package com.eduk.finance.service.domain;

import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.mapper.FinanceDataMapper;
import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.finance.service.domain.outbox.scheduler.ConfirmationOutboxHelper;
import com.eduk.finance.service.domain.ports.output.message.publisher.FinanceApprovalResponseMessagePublisher;
import com.eduk.finance.service.domain.ports.output.repository.ConfirmationApprovalRepository;
import com.eduk.finance.service.domain.ports.output.repository.FinanceRepository;
import com.eduk.finance.service.domain.exception.FinanceNotFoundException;
import com.eduk.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FinanceApprovalRequestHelper {

    private final FinanceDomainService financeDomainService;
    private final FinanceDataMapper financeDataMapper;
    private final FinanceRepository financeRepository;
    private final ConfirmationApprovalRepository confirmationApprovalRepository;
    private final ConfirmationOutboxHelper confirmationOutboxHelper;
    private final FinanceApprovalResponseMessagePublisher financeApprovalResponseMessagePublisher;



    public FinanceApprovalRequestHelper(FinanceDomainService financeDomainService,
                                           FinanceDataMapper financeDataMapper,
                                           FinanceRepository financeRepository,
                                           ConfirmationApprovalRepository confirmationApprovalRepository,
                                           ConfirmationOutboxHelper confirmationOutboxHelper,
                                           FinanceApprovalResponseMessagePublisher
                                                   financeApprovalResponseMessagePublisher) {
        this.financeDomainService = financeDomainService;
        this.financeDataMapper = financeDataMapper;
        this.financeRepository = financeRepository;
        this.confirmationApprovalRepository = confirmationApprovalRepository;
        this.confirmationOutboxHelper = confirmationOutboxHelper;
        this.financeApprovalResponseMessagePublisher = financeApprovalResponseMessagePublisher;
    }

    @Transactional
    public void persistConfirmationApproval(FinanceApprovalRequest financeApprovalRequest) {
        if (publishIfOutboxMessageProcessed(financeApprovalRequest)) {
            log.info("An outbox message with saga id: {} already saved to database!",
                    financeApprovalRequest.getSagaId());
            return;
        }

        log.info("Processing finance approval for confirmation id: {}", financeApprovalRequest.getConfirmationId());
        List<String> failureMessages = new ArrayList<>();
        Finance finance = findFinance(financeApprovalRequest);
        ConfirmationApprovalEvent confirmationApprovalEvent =
                financeDomainService.validateConfirmation(
                        finance,
                        failureMessages);
        confirmationApprovalRepository.save(finance.getConfirmationApproval());

        confirmationOutboxHelper
                .saveConfirmationOutboxMessage(financeDataMapper.confirmationApprovalEventToConfirmationEventPayload(confirmationApprovalEvent),
                        confirmationApprovalEvent.getConfirmationApproval().getApprovalStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(financeApprovalRequest.getSagaId()));

    }

    private Finance findFinance(FinanceApprovalRequest financeApprovalRequest) {
        Finance finance = financeDataMapper
                .financeApprovalRequestToFinance(financeApprovalRequest);
        Optional<Finance> financeResult = financeRepository.findFinanceInformation(finance);
        if (financeResult.isEmpty()) {
            log.error("Finance with id " + finance.getId().getValue() + " not found!");
            throw new FinanceNotFoundException("Finance with id " + finance.getId().getValue() +
                    " not found!");
        }

        Finance financeEntity = financeResult.get();
        finance.setActive(financeEntity.isActive());
        finance.getConfirmationDetail().getProducts().forEach(product ->
                financeEntity.getConfirmationDetail().getProducts().forEach(p -> {
                    if (p.getId().equals(product.getId())) {
                        product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
                    }
                }));
        finance.getConfirmationDetail().setId(new ConfirmationId(UUID.fromString(financeApprovalRequest.getConfirmationId())));

        return finance;
    }

    private boolean publishIfOutboxMessageProcessed(FinanceApprovalRequest financeApprovalRequest) {
        Optional<ConfirmationOutboxMessage> confirmationOutboxMessage =
                confirmationOutboxHelper.getCompletedConfirmationOutboxMessageBySagaIdAndOutboxStatus(UUID
                        .fromString(financeApprovalRequest.getSagaId()), OutboxStatus.COMPLETED);
        if (confirmationOutboxMessage.isPresent()) {
            financeApprovalResponseMessagePublisher.publish(confirmationOutboxMessage.get(),
                    confirmationOutboxHelper::updateOutboxStatus);
            return true;
        }
        return false;
    }
}
