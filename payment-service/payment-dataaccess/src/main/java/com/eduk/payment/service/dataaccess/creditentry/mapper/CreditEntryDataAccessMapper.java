package com.eduk.payment.service.dataaccess.creditentry.mapper;

import com.eduk.admission.service.domain.valueobject.ApplicationId;
import com.eduk.admission.service.domain.valueobject.Money;
import com.eduk.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import com.eduk.payment.service.domain.entity.CreditEntry;
import com.eduk.payment.service.domain.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .creditEntryId(new CreditEntryId(creditEntryEntity.getId()))
                .applicationId(new ApplicationId(creditEntryEntity.getApplicationId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .applicationId(creditEntry.getApplicationId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }
}
