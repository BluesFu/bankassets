package com.core.bankassets.task;

import com.core.bankassets.entities.Loan;
import com.core.bankassets.service.LoanService;
import com.core.bankassets.utils.DayGap;
import lombok.extern.apachecommons.CommonsLog;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author fsy
 */
@Component
@Configuration
@EnableScheduling
@CommonsLog
public class DailyInterestTask {
    @Resource
    private LoanService loanService;

    @Scheduled(cron="0 15 0 * * ?")
    private  void  Calculateinterest () throws ParseException {
        List<Loan> loanList= loanService.getAllLoan();
        if (loanList.size()==0){
            return;
        }
        for (int i = 0; i <loanList.size() ; i++) {
         /*   //已到期贷款不计息
            if(DayGap.getDayDiffer(loanList.get(i).getEndDate())<0){
                break;
            }*/
            if (DayGap.getDayDiffer(loanList.get(i).getBeginDate())<=0){
                break;
            }
           BigDecimal dayRate = loanList.get(i).getRate().divide(BigDecimal.valueOf(360*100),5, RoundingMode.DOWN) ;
           BigDecimal dayInterest= loanList.get(i).getPrincipal().multiply(dayRate);
           int dayGap= DayGap.getDayDiffer(loanList.get(i).getBeginDate());
           BigDecimal sumInterest=loanList.get(i).getInterest().add(dayInterest.multiply(BigDecimal.valueOf(dayGap)));
           loanList.get(i).setInterest(sumInterest);
           loanList.get(i).setBeginDate(new Date());
           loanService.saveLoan(loanList.get(i));
        }
    }
}
