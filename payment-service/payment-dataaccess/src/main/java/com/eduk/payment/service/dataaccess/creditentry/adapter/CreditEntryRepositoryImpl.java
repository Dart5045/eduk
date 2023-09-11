package com.eduk.payment.service.dataaccess.creditentry.adapter;

import com.eduk.payment.service.dataaccess.creditentry.mapper.CreditEntryDataAccessMapper;
import com.eduk.payment.service.dataaccess.creditentry.repository.CreditEntryJpaRepository;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.paymenet.service.domain.ports.output.repository.CreditEntryRepository;
import com.eduk.payment.service.domain.entity.CreditEntry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    public CreditEntryRepositoryImpl(CreditEntryJpaRepository creditEntryJpaRepository,
                                     CreditEntryDataAccessMapper creditEntryDataAccessMapper) {
        this.creditEntryJpaRepository = creditEntryJpaRepository;
        this.creditEntryDataAccessMapper = creditEntryDataAccessMapper;
    }

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper
                .creditEntryEntityToCreditEntry(creditEntryJpaRepository
                        .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry)));
    }


    @Override
    public Optional<CreditEntry> findByApplicationId(ApplicationId applicationId) {
        return creditEntryJpaRepository
                .findByApplicationId(applicationId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
