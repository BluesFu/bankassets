package com.core.bankassets.dao;

import com.core.bankassets.entities.Teller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fsy
 */
@Repository
public interface TellerRepository extends JpaRepository<Teller, Integer> {

    /**通过姓名和密码查询teller对象
     * @param name
     * @param password
     * @return boolean
     */
    Teller findByNameAndPassword(String name, String password);
}
