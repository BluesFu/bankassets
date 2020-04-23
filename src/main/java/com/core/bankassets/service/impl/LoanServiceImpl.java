package com.core.bankassets.service.impl;

import com.core.bankassets.dao.LoanRepository;
import com.core.bankassets.entities.Loan;
import com.core.bankassets.service.LoanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Resource
    private LoanRepository loanRepository;
    @Override
    public Loan saveLoan(Loan loan) {
      return   loanRepository.save(loan);
    }

    @Override
    public Loan getLoan(String loanId) {
        return loanRepository.findLoanByLoanId(loanId);
    }

    @Override
    public List<Loan> getAllLoan() {
        return loanRepository.findAll();
    }

}
