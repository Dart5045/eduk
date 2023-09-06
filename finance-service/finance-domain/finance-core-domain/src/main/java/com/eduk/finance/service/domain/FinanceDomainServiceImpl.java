package com.eduk.finance.service.domain;

import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.eduk.domain.DomainConstants.UTC;

@Slf4j
public class FinanceDomainServiceImpl implements FinanceDomainService {

    @Override
    public ConfirmationApprovalEvent validateConfirmation
            (Finance finance
                    , List<String> failureMessages,
             DomainEventPublisher<ConfirmationApprovedEvent>
                     confirmationApprovedEventDomainEventPublisher,
             DomainEventPublisher<ConfirmationRejectedEvent>
                     confirmationRejectedEventDomainEventPublisher) {
        finance.validateConfirmation(failureMessages);
        log.info("Validating confirmation with id: {}", finance.getConfirmationDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Confirmation is approved for confirmation id: {}", finance.getConfirmationDetail().getId().getValue());
            finance.constructConfirmationApproval(ConfirmationApprovalStatus.APPROVED);
            return new ConfirmationApprovedEvent(finance.getConfirmationApproval(),
                    finance.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)),
                    confirmationApprovedEventDomainEventPublisher);


        } else {
            log.info("Confirmation is rejected for confirmation id: {}", finance.getConfirmationDetail().getId().getValue());
            finance.constructConfirmationApproval(ConfirmationApprovalStatus.REJECTED);
            return new ConfirmationRejectedEvent(finance.getConfirmationApproval(),
                    finance.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)),
                    confirmationRejectedEventDomainEventPublisher);
        }
    }
}
