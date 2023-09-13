package com.eduk.finance.service.dataaccess.finance.adapter;

import com.eduk.finance.service.dataaccess.finance.mapper.FinanceDataAccessMapper;
import com.eduk.finance.service.dataaccess.finance.repository.ConfirmationApprovalJpaRepository;
import com.eduk.finance.service.domain.entity.ConfirmationApproval;
import com.eduk.finance.service.domain.ports.output.repository.ConfirmationApprovalRepository;
import org.springframework.stereotype.Component;


@Component
public class ConfirmationApprovalRepositoryImpl implements ConfirmationApprovalRepository {

    private final ConfirmationApprovalJpaRepository confirmationApprovalJpaRepository;
    private final FinanceDataAccessMapper financeDataAccessMapper;

    public ConfirmationApprovalRepositoryImpl(ConfirmationApprovalJpaRepository confirmationApprovalJpaRepository,
                                              FinanceDataAccessMapper financeDataAccessMapper) {
        this.confirmationApprovalJpaRepository = confirmationApprovalJpaRepository;
        this.financeDataAccessMapper = financeDataAccessMapper;
    }

    @Override
    public ConfirmationApproval save(ConfirmationApproval confirmationApproval) {
        return financeDataAccessMapper
                .confirmationApprovalEntityToConfirmationApproval(confirmationApprovalJpaRepository
                        .save(financeDataAccessMapper.confirmationApprovalToConfirmationApprovalEntity(confirmationApproval)));
    }

}
