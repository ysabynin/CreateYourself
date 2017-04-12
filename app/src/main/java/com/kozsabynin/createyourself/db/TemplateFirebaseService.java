package com.kozsabynin.createyourself.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class TemplateFirebaseService {
    public static DatabaseReference getTemplateRef(){
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference("users")
                .child(firebaseAuth.getUid()).child("template");
    }
    public void insertTemplate(Template template){
        DatabaseReference templateRef = getTemplateRef();
        String id = getTemplateRef().push().getKey();
        template.setId(id);
        Map<String, Object> templateValue = new HashMap<>();
        templateValue.put("/" + id, template.toMap());
        templateRef.updateChildren(templateValue);
    }

    public void updateTemplate(Template template){
        DatabaseReference templateRef = getTemplateRef();
        String id = template.getId();
        Map<String, Object> templateValue = new HashMap<>();
        templateValue.put("/" + id, template.toMap());
        templateRef.updateChildren(templateValue);
    }

    public void deleteTemplate(Template template){
        DatabaseReference templateRef = getTemplateRef();
        templateRef.child(template.getId()).removeValue();
    }
}
