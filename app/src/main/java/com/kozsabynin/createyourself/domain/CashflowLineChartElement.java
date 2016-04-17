package com.kozsabynin.createyourself.domain;

/**
 * Created by Evgeni Developer on 16.04.2016.
 */
public class CashflowLineChartElement {
    private double moneyAmount;
    private int monthNumber;

    public CashflowLineChartElement(double moneyAmount, int monthNumber) {
        this.moneyAmount = moneyAmount;
        this.monthNumber = monthNumber;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }
}
