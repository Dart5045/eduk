package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.entity.Finance;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;

import java.util.List;

public interface ConfirmationDomainService {

    ConfirmationCreatedEvent validateAndInitiateConfirmation(Confirmation confirmation, Finance restaurant);

    ConfirmationPaidEvent payConfirmation(Confirmation confirmation);

    void approveConfirmation(Confirmation confirmation);

    ConfirmationCancelledEvent cancelConfirmationPayment(Confirmation confirmation, List<String> failureMessages);

    void cancelConfirmation(Confirmation confirmation, List<String> failureMessages);
}
