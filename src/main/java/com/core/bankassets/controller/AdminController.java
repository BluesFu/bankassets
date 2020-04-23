package com.core.bankassets.controller;

import com.alibaba.fastjson.JSONObject;
import com.core.bankassets.entities.Debt;
import com.core.bankassets.entities.Loan;
import com.core.bankassets.utils.*;
import com.core.bankassets.entities.Teller;
import com.core.bankassets.service.AssetsService;
import com.core.bankassets.service.DebtService;
import com.core.bankassets.service.LoanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author fsy
 */
@RestController
@RequestMapping("dev-api/admin/")
public class AdminController {

    @Resource
    private DebtService debtService;
    @Resource
    private LoanService loanService;
    @Resource
    private AssetsService assetsService;

    @PostMapping("addTeller")
    public Teller addTeller(@RequestBody String tellerObj){
        JSONObject tellerJson=JSONObject.parseObject(tellerObj);
        Teller teller=tellerJson.toJavaObject(Teller.class);
        return assetsService.addTeller(teller);
    }

    @PostMapping("addDebt")
    public Debt addDebtAccount(@RequestBody String debtObj){
        JSONObject debtJson=JSONObject.parseObject(debtObj);
        Debt debt=debtJson.toJavaObject(Debt.class);
        debt.setDebtId(DebtId.getDebtId());
        return debtService.saveDebt(debt);
    }

    @PostMapping("addLoan")
    @ResponseBody
    public Loan addLoanAccount(@RequestBody String loanObj){
        JSONObject loanJson=JSONObject.parseObject(loanObj);
        Loan loan=loanJson.toJavaObject(Loan.class);
        loan.setLoanId(LoanId.getLoanId());
        Debt debt= debtService.getDebt(loan.getDebtId());
        debt.setAmount(debt.getAmount().add(loan.getPrincipal()));
        debtService.saveDebt(debt);
        return loanService.saveLoan(loan);
    }

    @PostMapping("repay")
    public Loan repayment(@RequestParam("loanId") String loanId,
                          @RequestParam("principal")String principal,
                          @RequestParam("interest")String interest,
                          @RequestParam("debtId")String debtId){
        BigDecimal sumAmount=BigDecimal.valueOf(Long.parseLong(principal)).add(BigDecimal.valueOf(Long.parseLong(interest)));
        Loan loan=loanService.getLoan(loanId);
        //不能超额还款
        if(loan.getPrincipal().compareTo(new BigDecimal(principal)) == -1||
                loan.getInterest().compareTo(new BigDecimal(interest))==-1){
            return null;
        }
        Debt debt=debtService.getDebt(debtId);
        //还款账号金额需要足够
        if (debt.getAmount().compareTo(sumAmount)==-1){
            return null;
        }
        BigDecimal newPrincipal=loan.getPrincipal().subtract(new BigDecimal(principal));
        BigDecimal newInterest=loan.getInterest().subtract(new BigDecimal(interest));
        loan.setPrincipal(newPrincipal);
        loan.setInterest(newInterest);
        debt.setAmount(debt.getAmount().subtract(sumAmount));
        debtService.saveDebt(debt);
        return loanService.saveLoan(loan);
    }

    @PostMapping("trial")
    public Repay trial(@RequestParam("principal") String principal,
                       @RequestParam("rate")String rate,
                       @RequestParam("cycle") String cycle,
                       @RequestParam("type")String type){
        double invest=Double.parseDouble(principal);
        double yearRate=Double.parseDouble(rate)/100;
        int totalMonth=Integer.parseInt(cycle);
        if ("bx".equals(type)){
            return PrincipalAndInterestEquals.getResult(invest,yearRate,totalMonth);
        }
        if ("bj".equals(type)){
            return AverageCapitalUtils.getResult(invest,yearRate,totalMonth);
        }
       return null;
    }

    @PostMapping("debtQuery")
    public Debt debtQuery(@RequestParam("code")String debtId){
        Debt debt=debtService.getDebt(debtId);
        if (debt!=null){
            return debt;
        }
        return null;
    }

    @PostMapping("loanQuery")
    public Loan loanQuery(@RequestParam("code")String loanId){
        Loan loan=loanService.getLoan(loanId);
        if (loan!=null){
            return loan;
        }
        return null;
    }
}
