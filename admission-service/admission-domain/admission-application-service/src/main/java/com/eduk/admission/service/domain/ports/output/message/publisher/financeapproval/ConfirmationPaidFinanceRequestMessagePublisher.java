package com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval;

import com.eduk.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationPaidFinanceRequestMessagePublisher
extends DomainEventPublisher<ConfirmationPaidEvent> {
}
