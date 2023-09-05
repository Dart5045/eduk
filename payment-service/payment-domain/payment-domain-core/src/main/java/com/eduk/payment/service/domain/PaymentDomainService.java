package com.eduk.payment.service.domain;

import com.eduk.payment.service.domain.entity.CreditEntry;
import com.eduk.payment.service.domain.entity.CreditHistory;
import com.eduk.payment.service.domain.entity.Payment;
import com.eduk.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {


    PaymentEvent validateAndCancelPayment(Payment payment
            , CreditEntry creditEntry
            , List<CreditHistory> creditHistories
            , List<String> failureMessages);

    PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages);
}
