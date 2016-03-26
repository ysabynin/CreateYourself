package com.kozsabynin.createyourself.domain;

/**
 * Created by Evgeni Developer on 26.03.2016.
 */
public enum CashType {
    INCOME("I"),
    EXPENSE("E");

    private String text;

    CashType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
