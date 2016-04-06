package com.kozsabynin.createyourself.domain;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
public class Category implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private Integer id;
    private String title;
    private CashType type;

    public Category() {
    }

    public Category(String title, CashType type) {
        this.title = title;
        this.type = type;
    }

    public Category(Integer id, String title, CashType type) {
        this.id = id;
        this.title = title;
        this.type = type;
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
}
