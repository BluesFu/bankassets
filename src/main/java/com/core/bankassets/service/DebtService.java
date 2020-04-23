package com.core.bankassets.service;

import com.core.bankassets.entities.Debt;

/**
 * @author fsy
 */
public interface DebtService {

    /**保存debt对象信息
     * @param debt
     * @return debt
     */
    Debt saveDebt(Debt debt);

    /**通过debtId获取debt对象
     * @param debtId
     * @return debt
     */
    Debt getDebt(String debtId);
}
