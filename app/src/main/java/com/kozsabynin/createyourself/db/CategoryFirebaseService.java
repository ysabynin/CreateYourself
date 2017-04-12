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
    private static FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
    private static DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("users")
            .child(firebaseAuth.getUid()).child("category");

    public static DatabaseReference getCategoryRef(){
        return categoryRef;
    }
    
    public void insertCategory(Category category){
        DatabaseReference categoryRef = getCategoryRef();
        String id = categoryRef.push().getKey();
        category.setId(id);
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        categoryRef.updateChildren(categoryValue);
    }

    public void updateCategory(Category category){
        DatabaseReference categoryRef = getCategoryRef();
        String id = category.getId();
        Map<String, Object> categoryValue = new HashMap<>();
        categoryValue.put("/" + id, category.toMap());
        categoryRef.updateChildren(categoryValue);
    }

    public void deleteCategory(Category category){
        DatabaseReference categoryRef = getCategoryRef();
        categoryRef.child(category.getId()).removeValue();
    }
}
