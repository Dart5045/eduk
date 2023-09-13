package com.eduk.finance.service.dataaccess.finance.adapter;

import com.eduk.dataaccess.finance.entity.FinanceEntity;
import com.eduk.dataaccess.finance.repository.FinanceJpaRepository;
import com.eduk.finance.service.dataaccess.finance.mapper.FinanceDataAccessMapper;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.ports.output.repository.FinanceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FiananceRepositoryImpl implements FinanceRepository {

    private final FinanceJpaRepository financeJpaRepository;
    private final FinanceDataAccessMapper financeDataAccessMapper;

    public FiananceRepositoryImpl(FinanceJpaRepository financeJpaRepository,
                                  FinanceDataAccessMapper financeDataAccessMapper) {
        this.financeJpaRepository = financeJpaRepository;
        this.financeDataAccessMapper = financeDataAccessMapper;
    }

    @Override
    public Optional<Finance> findFinanceInformation(Finance finance) {
        List<UUID> financeProducts =
                financeDataAccessMapper.financeToFinanceProducts(finance);
        Optional<List<FinanceEntity>> financeEntities = financeJpaRepository
                .findByFinanceIdAndProductIdIn(finance.getId().getValue(),
                        financeProducts);
        return financeEntities.map(financeDataAccessMapper::financeEntityToFinance);
    }
}
