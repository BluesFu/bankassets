package com.core.bankassets.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fsy
 */
@Entity
@Data
@Getter
@Setter
public class Debt {
    @Id
    private String debtId;
    private String username;
    private String type;
    private Date date;
    private String address;
    private String phone;
    private BigDecimal amount;
}
