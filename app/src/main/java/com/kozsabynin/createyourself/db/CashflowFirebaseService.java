package com.kozsabynin.createyourself.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class CashflowFirebaseService {
    public static DatabaseReference getCashflowRef(){
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference("users")
                .child(firebaseAuth.getUid()).child("cashflow");
    }

    public void insertCashflow(Cashflow cashflow){
        DatabaseReference cashflowRef = getCashflowRef();
        String id = cashflowRef.push().getKey();
        cashflow.setId(id);
        Map<String, Object> cashflowValue = new HashMap<>();
        cashflowValue.put("/" + id, cashflow.toMap());
        cashflowRef.updateChildren(cashflowValue);
    }

    public void updateCashflow(Cashflow cashflow){
        DatabaseReference cashflowRef = getCashflowRef();
        String id = cashflow.getId();
        Map<String, Object> cashflowValue = new HashMap<>();
        cashflowValue.put("/" + id, cashflow.toMap());
        cashflowRef.updateChildren(cashflowValue);
    }

    public void deleteCashflow(Cashflow cashflow){
        DatabaseReference cashflowRef = getCashflowRef();
        cashflowRef.child(cashflow.getId()).removeValue();
    }
}
