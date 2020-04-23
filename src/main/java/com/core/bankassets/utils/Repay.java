package com.core.bankassets.utils;

import com.core.bankassets.utils.Cycle;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fsy
 */
@Data
@Getter
@Setter
public class Repay {
    List<Cycle> cycles;
    private BigDecimal sumInterest;
    private BigDecimal amount;
}
