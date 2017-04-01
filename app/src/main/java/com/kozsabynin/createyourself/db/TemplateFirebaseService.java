package com.kozsabynin.createyourself.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class TemplateFirebaseService {
    DatabaseReference templateRef = FirebaseDatabase.getInstance().getReference("template");

    public void insertTemplate(Template template){
        String id = templateRef.push().getKey();
        template.setId(id);
        Map<String, Object> templateValue = new HashMap<>();
        templateValue.put("/" + id, template.toMap());
        templateRef.updateChildren(templateValue);
    }

    public void updateTemplate(Template template){
        String id = template.getId();
        Map<String, Object> templateValue = new HashMap<>();
        templateValue.put("/" + id, template.toMap());
        templateRef.updateChildren(templateValue);
    }

    public void deleteTemplate(Template template){
        templateRef.child(template.getId()).removeValue();
    }
}
