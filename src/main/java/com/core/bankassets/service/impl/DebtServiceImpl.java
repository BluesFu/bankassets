package com.core.bankassets.service.impl;

import com.core.bankassets.dao.DebtRepository;
import com.core.bankassets.entities.Debt;
import com.core.bankassets.service.DebtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fsy
 */
@Service
public class DebtServiceImpl implements DebtService {
    @Resource
    private DebtRepository debtRepository;
    @Override
    public Debt saveDebt(Debt debt) {
       return debtRepository.save(debt);
    }
    @Override
    public Debt getDebt(String debtId) {
        return debtRepository.findDebtBydebtId(debtId);
    }
}
