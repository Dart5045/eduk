package com.eduk.admission.service.domain.outbox.scheduler.approval;

import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval.FinanceApprovalRequestMessagePublisher;
import com.eduk.outbox.OutboxScheduler;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FinanceApprovalOutboxScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final FinanceApprovalRequestMessagePublisher fiananceApprovalRequestMessagePublisher;

    public FinanceApprovalOutboxScheduler(ApprovalOutboxHelper
                                                     approvalOutboxHelper,
                                          FinanceApprovalRequestMessagePublisher
                                                     fiananceApprovalRequestMessagePublisher) {
        this.approvalOutboxHelper = approvalOutboxHelper;
        this.fiananceApprovalRequestMessagePublisher = fiananceApprovalRequestMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${confirmation-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${confirmation-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<ConfirmationApprovalOutboxMessage>> outboxMessagesResponse =
                approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.PROCESSING);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            List<ConfirmationApprovalOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} ConfirmationApprovalOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage ->
                    fiananceApprovalRequestMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
            log.info("{} ConfirmationApprovalOutboxMessage sent to message bus!", outboxMessages.size());

        }
    }

    private void updateOutboxStatus(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage, OutboxStatus outboxStatus) {
        confirmationApprovalOutboxMessage.setOutboxStatus(outboxStatus);
        approvalOutboxHelper.save(confirmationApprovalOutboxMessage);
        log.info("ConfirmationApprovalOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
