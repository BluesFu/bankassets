package com.core.bankassets.service.impl;

import com.core.bankassets.dao.TellerRepository;
import com.core.bankassets.entities.Teller;
import com.core.bankassets.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author fsy
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private TellerRepository tellerRepository;
    @Override
    public Teller login(Teller teller) {
        return  tellerRepository.findByNameAndPassword(teller.getName(),teller.getPassword());
    }
}
