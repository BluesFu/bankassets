package com.core.bankassets.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fsy
 */
public class AverageCapitalUtils {
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金和利息
     *
     * 公式：每月偿还本金=(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位
     */
    private static Map<Integer, Double> getPerMonthPrincipalInterest(double invest, double yearRate, int totalMonth) {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        // 每月本金
        double monthPri = getPerMonthPrincipal(invest, totalMonth);
        // 获取月利率
        double monthRate = yearRate / 12;
        monthRate = new BigDecimal(monthRate).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        for (int i = 1; i <= totalMonth; i++) {
            double monthRes = monthPri + (invest - monthPri * (i - 1)) * monthRate;
            monthRes = new BigDecimal(monthRes).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            map.put(i, monthRes);
        }
        return map;
    }
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还利息
     *
     * 公式：每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 每月偿还利息
     */
    private static Map<Integer, Double> getPerMonthInterest(double invest, double yearRate, int totalMonth) {
        Map<Integer, Double> inMap = new HashMap<Integer, Double>();
        double principal = getPerMonthPrincipal(invest, totalMonth);
        Map<Integer, Double> map = getPerMonthPrincipalInterest(invest, yearRate, totalMonth);
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            BigDecimal principalBigDecimal = new BigDecimal(principal);
            BigDecimal principalInterestBigDecimal = new BigDecimal(entry.getValue());
            BigDecimal interestBigDecimal = principalInterestBigDecimal.subtract(principalBigDecimal);
            interestBigDecimal = interestBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
            inMap.put(entry.getKey(), interestBigDecimal.doubleValue());
        }
        return inMap;
    }

    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金
     *
     * 公式：每月应还本金=贷款本金÷还款月数
     *
     * @param invest
     *            总借款额（贷款本金）
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 每月偿还本金
     */
    private static double getPerMonthPrincipal(double invest, int totalMonth) {
        BigDecimal monthIncome = new BigDecimal(invest).divide(new BigDecimal(totalMonth), 2, BigDecimal.ROUND_DOWN);
        return monthIncome.doubleValue();
    }

    /**
     * 等额本金计算获取还款方式为等额本金的总利息
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 总利息
     */
    private static double getInterestCount(double invest, double yearRate, int totalMonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, Double> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);

        for (Map.Entry<Integer, Double> entry : mapInterest.entrySet()) {
            count = count.add(new BigDecimal(entry.getValue()));
        }
        return count.doubleValue();
    }

    public static Repay getResult(double invest, double yearRate, int totalmonth){
        Repay repay=new Repay();
        List<Cycle> cycles=new ArrayList<>();
        double perMonthPrincipal =getPerMonthPrincipal(invest,totalmonth);
        Map<Integer ,Double> perMonthInterest=getPerMonthInterest(invest,yearRate,totalmonth);

        for (Integer i = 1; i <  perMonthInterest.size(); i++) {
            Cycle cycle=new Cycle();
            cycle.setPrincipal(BigDecimal.valueOf(perMonthPrincipal));
            cycle.setInterest(BigDecimal.valueOf(perMonthInterest.get(i)));
            cycle.setInvest(BigDecimal.valueOf(perMonthPrincipal+perMonthInterest.get(i)));
            cycles.add(cycle);
        }
        repay.setCycles(cycles);
        repay.setSumInterest(BigDecimal.valueOf(getInterestCount(invest,yearRate,totalmonth)));
        repay.setAmount(BigDecimal.valueOf(invest+getInterestCount(invest,yearRate,totalmonth)));
        return repay;
    }

}
