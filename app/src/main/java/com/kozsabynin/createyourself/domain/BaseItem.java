package com.kozsabynin.createyourself.domain;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */
public class BaseItem {
    private String title;
    private Integer cost;

    public BaseItem(String title, Integer cost) {
        this.title = title;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
