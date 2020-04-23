package com.core.bankassets.service;

import com.core.bankassets.entities.Loan;

import java.util.List;

/**
 * @author fsy
 */
public interface LoanService {

    /**保存loan对象信息
     * @param loan
     * @return Loan
     */
    Loan saveLoan(Loan loan);

    /**通过loanId获取loan对象
     * @param loanId
     * @return Loan
     */
    Loan getLoan(String loanId);

    /**获取所有loan对象信息
     * @return List<Loan>
     */
    List<Loan> getAllLoan();
}
