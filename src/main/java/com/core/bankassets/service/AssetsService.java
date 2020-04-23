package com.core.bankassets.service;

import com.core.bankassets.entities.Loan;
import com.core.bankassets.entities.Teller;

/**
 * @author fsy
 */
public interface AssetsService {

    /**添加柜员
     * @param teller
     * @return teller
     */
    Teller addTeller(Teller teller);

}
