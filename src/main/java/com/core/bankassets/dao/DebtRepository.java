package com.core.bankassets.dao;

import com.core.bankassets.entities.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fsy
 */
@Repository
public interface DebtRepository extends JpaRepository<Debt, String> {
    /**用负债账号查找对象
     * @param debtId
     * @return debt
     */
    Debt findDebtBydebtId(String debtId);
}
