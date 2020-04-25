package com.core.bankassets.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fsy
 */
public class PrincipalAndInterestEquals {
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
     *
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位
     */
    private static double getPerMonthPrincipalInterest(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalmonth) - 1), 2,  RoundingMode.DOWN);
        return monthIncome.doubleValue();
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     *
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还利息
     */
    private static Map<Integer, BigDecimal> getPerMonthInterest(double invest, double yearRate, int totalmonth) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        double monthRate = yearRate/12;
        BigDecimal monthInterest;
        for (int i = 1; i < totalmonth + 1; i++) {
            BigDecimal multiply = new BigDecimal(invest).multiply(new BigDecimal(monthRate));
            BigDecimal sub  = BigDecimal.valueOf(Math.pow(1 + monthRate, totalmonth)).subtract(BigDecimal.valueOf(Math.pow(1 + monthRate, i - 1)));
            monthInterest = multiply.multiply(sub).divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalmonth) - 1), 6,  RoundingMode.DOWN);
            monthInterest = monthInterest.setScale(2,  RoundingMode.DOWN);
            map.put(i, monthInterest);
        }
        return map;
    }
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还本金
     */
    private static Map<Integer, BigDecimal> getPerMonthPrincipal(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalmonth) - 1), 2,  RoundingMode.DOWN);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);
        Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
        }
        return mapPrincipal;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的总利息
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 总利息
     */
    private static double getInterestCount(double invest, double yearRate, int totalmonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }

    /**
     * 应还本金总和
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 应还本金总和
     */
    private static double getPrincipalInterestCount(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal perMonthInterest = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalmonth) - 1), 2,  RoundingMode.DOWN);
        BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalmonth));
        count = count.setScale(2,  RoundingMode.DOWN);
        return count.doubleValue();
    }
    /**
     * 返回结果
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 计算结果
     */
    public static Repay getResult(double invest, double yearRate, int totalmonth){
        Repay repay=new Repay();
        List<Cycle> cycles=new ArrayList<>();
        Map<Integer, BigDecimal>  perMonthPrincipals= getPerMonthPrincipal(invest,  yearRate, totalmonth);
        Map<Integer, BigDecimal>  perMonthInterests=getPerMonthInterest(invest,  yearRate, totalmonth);
        for (Integer i = 1; i <=perMonthPrincipals.size() ; i++) {
           Cycle cycle=  new Cycle();
           cycle.setCycleNum(i);
           cycle.setPrincipal(perMonthPrincipals.get(i));
           cycle.setInterest(perMonthInterests.get(i));
           cycle.setInvest(perMonthPrincipals.get(i).add(perMonthInterests.get(i)));
           cycles.add(cycle);
        }
        repay.setCycles(cycles);
        repay.setAmount(BigDecimal.valueOf(getPrincipalInterestCount(invest,  yearRate, totalmonth)));
        repay.setSumInterest(BigDecimal.valueOf(getInterestCount( invest,  yearRate,  totalmonth)));
        return repay;
    }

}
