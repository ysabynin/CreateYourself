package com.kozsabynin.createyourself.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Category;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class CategoryFirebaseService {
    DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("category");

    public void insertCategory(Category category){
        String id = categoryRef.push().getKey();
        category.setId(id);
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        categoryRef.updateChildren(categoryValue);
    }

    public void updateCategory(Category category){
        String id = category.getId();
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        categoryRef.updateChildren(categoryValue);
    }

    public void deleteCategory(Category category){
        categoryRef.child(category.getId()).removeValue();
    }
}
