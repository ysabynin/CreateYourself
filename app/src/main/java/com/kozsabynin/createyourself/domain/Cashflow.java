package com.kozsabynin.createyourself.domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */
@IgnoreExtraProperties
public class Cashflow implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private String id;
    private String title;
    private CashType type;
    private Double cost;
    private Calendar date;
    private Category category;

    public Cashflow() {
    }

    public Cashflow(String title, CashType type, Double cost, Calendar date, Category category) {
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.category = category;
    }

    public Cashflow(String id, String title, CashType type, Double cost, Calendar date, Category category) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.category = category;
    }

    public String getId() {
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

    public String getType() {
        if (type == null) {
            return null;
        } else {
            return type.getText();
        }
    }

    public void setType(String newType) {
        if (newType == null) {
            type = null;
        } else {
            this.type = CashType.getByText(newType);
        }
    }

    public long getDate() {
        return date.getTimeInMillis();
    }

    public void setDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp);
        this.date = calendar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category == null ? new Category("Unknown",type) : category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("type", type.getText());
        result.put("cost", cost);
        result.put("date", date.getTimeInMillis());
        if(category != null)
            result.put("category", category.toMap());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cashflow cashflow = (Cashflow) o;

        return !(id != null ? !id.equals(cashflow.id) : cashflow.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}