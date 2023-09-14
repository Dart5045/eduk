package com.eduk.paymenet.service.domain.outbox.scheduler;

import com.eduk.outbox.OutboxScheduler;
import com.eduk.outbox.OutboxStatus;
import com.eduk.paymenet.service.domain.outbox.model.ConfirmationOutboxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ConfirmationOutboxCleanerScheduler implements OutboxScheduler {

    private final ConfirmationOutboxHelper confirmationOutboxHelper;

    public ConfirmationOutboxCleanerScheduler(ConfirmationOutboxHelper confirmationOutboxHelper) {
        this.confirmationOutboxHelper = confirmationOutboxHelper;
    }

    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<ConfirmationOutboxMessage>> outboxMessagesResponse =
                confirmationOutboxHelper.getConfirmationOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            List<ConfirmationOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} ConfirmationOutboxMessage for clean-up!", outboxMessages.size());
            confirmationOutboxHelper.deleteConfirmationOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
            log.info("Deleted {} ConfirmationOutboxMessage!", outboxMessages.size());
        }
    }
}
