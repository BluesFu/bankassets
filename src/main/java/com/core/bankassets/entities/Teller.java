package com.core.bankassets.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author fsy
 */
@Entity
@Data
@Setter
@Getter
public class Teller {
    @Id
    @GeneratedValue
    private Integer tellerId;
    private String name;
    private String password;


}
