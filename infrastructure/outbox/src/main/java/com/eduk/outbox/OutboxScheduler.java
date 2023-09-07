package com.eduk.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
