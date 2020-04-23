package com.core.bankassets.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author fsy
 */
@Data
@Getter
@Setter
public class Cycle {
    private Integer cycleNum;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal invest;
}
