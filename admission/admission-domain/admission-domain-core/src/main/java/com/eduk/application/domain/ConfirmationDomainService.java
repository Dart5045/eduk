package com.eduk.application.domain;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;

import java.util.List;

public interface ConfirmationDomainService {

    ConfirmationCreatedEvent validateAndInitiatePaymentFee(Confirmation confirmation);
    ConfirmationPaidEvent payFee(Application  application);
    void approvePaymentFee(Application application);
    ConfirmationCancelledEvent cancelFeePaymentEvent(Application application, List<String> failureMessages);
    void cancelPaymentFee(Application application, List<String> failureMessages);
}
