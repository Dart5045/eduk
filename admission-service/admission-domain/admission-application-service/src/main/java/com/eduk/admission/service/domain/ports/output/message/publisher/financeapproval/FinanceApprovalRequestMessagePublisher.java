package com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval;


import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface FinanceApprovalRequestMessagePublisher {

    void publish(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage,
                 BiConsumer<ConfirmationApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
