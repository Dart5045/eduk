package com.eduk.payment.domain.entity.entity;

import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.Money;
import com.eduk.domain.valueobject.PaymentId;

public class Payment extends AggregateRoot<PaymentId> {

    private Money amount;
}
