package com.core.bankassets.service;

import com.core.bankassets.entities.Teller;

/**
 * @author fsy
 */
public interface LoginService {

    /**
     * 返回登录结果
     * @param teller
     * @return Teller
     */
    Teller login(Teller teller);

}
