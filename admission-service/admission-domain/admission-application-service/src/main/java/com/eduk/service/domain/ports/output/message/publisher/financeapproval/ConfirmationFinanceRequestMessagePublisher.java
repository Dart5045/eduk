package com.eduk.service.domain.ports.output.message.publisher.financeapproval;

import com.eduk.application.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationFinanceRequestMessagePublisher
extends DomainEventPublisher<ConfirmationPaidEvent> {
}
