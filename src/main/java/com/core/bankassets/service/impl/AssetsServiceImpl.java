package com.core.bankassets.service.impl;

import com.core.bankassets.dao.LoanRepository;
import com.core.bankassets.dao.TellerRepository;
import com.core.bankassets.entities.Loan;
import com.core.bankassets.entities.Teller;
import com.core.bankassets.service.AssetsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fsy
 */
@Service
public class AssetsServiceImpl implements AssetsService {
    @Resource
    private TellerRepository tellerRepository;
    @Override
    public Teller addTeller(Teller teller) {
        return tellerRepository.save(teller);
    }

}
