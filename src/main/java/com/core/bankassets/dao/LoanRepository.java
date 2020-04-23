package com.core.bankassets.dao;

import com.core.bankassets.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fsy
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

    /**用贷款借据号查询贷款对象
     * @param LoanId
     * @return loan
     */
    Loan findLoanByLoanId(String LoanId);
}
