package com.eduk.finance.service.domain;

import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.exception.FinanceNotFoundException;
import com.eduk.finance.service.domain.mapper.FinanceDataMapper;
import com.eduk.finance.service.domain.ports.output.message.publisher.ConfirmationApprovedMessagePublisher;
import com.eduk.finance.service.domain.ports.output.message.publisher.ConfirmationRejectedMessagePublisher;
import com.eduk.finance.service.domain.ports.output.repository.ConfirmationApprovalRepository;
import com.eduk.finance.service.domain.ports.output.repository.FinanceRepository;
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
    private final ConfirmationApprovedMessagePublisher confirmationApprovedMessagePublisher;

    private final ConfirmationRejectedMessagePublisher confirmationRejectedMessagePublisher;

    public FinanceApprovalRequestHelper(FinanceDomainService financeDomainService,
                                        FinanceDataMapper financeDataMapper,
                                        FinanceRepository financeRepository,
                                        ConfirmationApprovalRepository confirmationApprovalRepository,
                                        ConfirmationApprovedMessagePublisher confirmationApprovedMessagePublisher,
                                        ConfirmationRejectedMessagePublisher confirmationRejectedMessagePublisher) {
        this.financeDomainService = financeDomainService;
        this.financeDataMapper = financeDataMapper;
        this.financeRepository = financeRepository;
        this.confirmationApprovalRepository = confirmationApprovalRepository;
        this.confirmationApprovedMessagePublisher = confirmationApprovedMessagePublisher;
        this.confirmationRejectedMessagePublisher = confirmationRejectedMessagePublisher;
    }


    @Transactional
    public ConfirmationApprovalEvent persistConfirmationApproval(
            FinanceApprovalRequest financeApprovalRequest) {
        log.info("Processing restaurant approval for confirmation id: {}", financeApprovalRequest.getConfirmationId());
        List<String> failureMessages = new ArrayList<>();
        Finance finance = findFinance(financeApprovalRequest);
        ConfirmationApprovalEvent confirmationApprovalEvent =
                financeDomainService.validateConfirmation(
                        finance,
                        failureMessages,
                        confirmationApprovedMessagePublisher,
                        confirmationRejectedMessagePublisher);
        confirmationApprovalRepository.save(finance.getConfirmationApproval());
        return confirmationApprovalEvent;
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
}
