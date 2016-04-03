package com.kozsabynin.createyourself.domain;

import java.util.Calendar;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
public class Template {

    private Integer id;
    private String title;
    private CashType type;
    private Category category;
    private Double cost;

    public Template(Integer id, String title, CashType type, Category category, Double cost) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.category = category;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CashType getType() {
        return type;
    }

    public void setType(CashType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
