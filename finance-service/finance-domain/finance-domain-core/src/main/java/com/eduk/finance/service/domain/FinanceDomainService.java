package com.eduk.finance.service.domain;

import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.util.List;

    public interface FinanceDomainService {

    ConfirmationApprovalEvent validateConfirmation(Finance finance,
                                                   List<String> failureMessages,
                                                   DomainEventPublisher<ConfirmationApprovedEvent> confirmationApprovedEventDomainEventPublisher,
                                                   DomainEventPublisher<ConfirmationRejectedEvent> confirmationRejectedEventDomainEventPublisher);

}
