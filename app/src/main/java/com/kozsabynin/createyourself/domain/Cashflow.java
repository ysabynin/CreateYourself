package com.kozsabynin.createyourself.domain;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */

public class Cashflow {
    private Integer id;
    private String title;
    private CashType type;
    private Double cost;

    public Cashflow(String title, CashType type, Double cost) {
        this.title = title;
        this.type = type;
        this.cost = cost;
    }

    public Cashflow(Integer id, String title, CashType type, Double cost) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public CashType getType() {
        return type;
    }

    public void setType(CashType type) {
        this.type = type;
    }
}