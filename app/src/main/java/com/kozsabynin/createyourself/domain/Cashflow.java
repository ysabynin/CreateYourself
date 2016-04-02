package com.kozsabynin.createyourself.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */

public class Cashflow implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private Integer id;
    private String title;
    private CashType type;
    private Double cost;
    private Calendar date;
    private boolean isTemplate;

    public Cashflow() {
    }

    public Cashflow(String title, CashType type, Double cost, Calendar date, boolean isTemplate) {
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.isTemplate = isTemplate;
    }

    public Cashflow(Integer id, String title, CashType type, Double cost, Calendar date, boolean isTemplate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.isTemplate = isTemplate;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(boolean isTemplate) {
        this.isTemplate = isTemplate;
    }
}