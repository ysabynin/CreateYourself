package com.kozsabynin.createyourself.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Category;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class CategoryFirebaseService {
    DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference("users");
    FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();

    public void insertCategory(Category category){
        String id = firebaseDB.push().getKey();
        category.setId(id);
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        firebaseDB.child(firebaseAuth.getUid()).child("category").updateChildren(categoryValue);
    }

    public void updateCategory(Category category){
        String id = category.getId();
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        firebaseDB.updateChildren(categoryValue);
    }

    public void deleteCategory(Category category){
        firebaseDB.child(category.getId()).removeValue();
    }
}
