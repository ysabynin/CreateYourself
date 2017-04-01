package com.kozsabynin.createyourself.domain;

/**
 * Created by Evgeni Developer on 12.04.2016.
 */
public class CashflowPieChartElement {
    private String title;
    private double totalCost;
    private int percentage;

    public CashflowPieChartElement(String title, double totalCost, int percentage) {
        this.title = title;
        this.totalCost = totalCost;
        this.percentage = percentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashflowPieChartElement that = (CashflowPieChartElement) o;

        return !(title != null ? !title.equals(that.title) : that.title != null);

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
