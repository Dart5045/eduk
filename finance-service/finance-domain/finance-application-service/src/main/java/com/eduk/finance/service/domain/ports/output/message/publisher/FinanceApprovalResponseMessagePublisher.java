package com.eduk.finance.service.domain.ports.output.message.publisher;


import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface FinanceApprovalResponseMessagePublisher {

    void publish(ConfirmationOutboxMessage confirmationOutboxMessage,
                 BiConsumer<ConfirmationOutboxMessage, OutboxStatus> outboxCallback);
}
