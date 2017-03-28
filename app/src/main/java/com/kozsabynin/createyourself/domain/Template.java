package com.kozsabynin.createyourself.domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
@IgnoreExtraProperties
public class Template implements Serializable{
    private static final long serialVersionUID = -7060210544600464481L;

    private String id;
    private String title;
    private CashType type;
    private Category category;
    private Double cost;

    public Template() {
    }

    public Template(String title, CashType type, Category category, Double cost) {
        this.title = title;
        this.type = type;
        this.category = category;
        this.cost = cost;
    }

    public Template(String id, String title, CashType type, Category category, Double cost) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.category = category;
        this.cost = cost;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("type", type.getText());
        result.put("cost", cost);
        if(category != null)
            result.put("category", category.toMap());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        return !(id != null ? !id.equals(template.id) : template.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
