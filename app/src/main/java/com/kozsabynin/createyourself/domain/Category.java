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
public class Category implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private String id;
    private String title;
    private CashType type;

    public Category() {
    }

    public Category(String title, CashType type) {
        this.title = title;
        this.type = type;
    }

    public Category(String id, String title, CashType type) {
        this.id = id;
        this.title = title;
        this.type = type;
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

    public CashType getCashType(){
        return type;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        if(type != null)
            result.put("type", type.getText());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return !(id != null ? !id.equals(category.id) : category.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
