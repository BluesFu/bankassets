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
public class Loan {
    @Id
    private String loanId;
    private Date beginDate;
    private Date endDate;
    private Integer cycle;
    private String debtId;
    private BigDecimal principal=BigDecimal.valueOf(0);
    private BigDecimal interest=BigDecimal.valueOf(0);
    private BigDecimal rate;
}
