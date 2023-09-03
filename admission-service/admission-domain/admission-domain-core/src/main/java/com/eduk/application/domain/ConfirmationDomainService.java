package com.eduk.application.domain;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;

import java.util.List;

public interface ConfirmationDomainService {

    ConfirmationCreatedEvent validateAndInitiatePaymentFee(Confirmation confirmation);
    ConfirmationPaidEvent payFee(Confirmation confirmation);
    void approveConfirmation(Confirmation application);
    ConfirmationCancelledEvent cancelFeePaymentEvent(Confirmation confirmation, List<String> failureMessages);
    void cancelPaymentFee(Confirmation confirmation, List<String> failureMessages);
}
