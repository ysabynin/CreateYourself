package com.kozsabynin.createyourself.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeni Developer on 29.03.2017.
 */
public class CashflowFirebaseService {
    DatabaseReference cashflowRef = FirebaseDatabase.getInstance().getReference("cashflow");

    public void insertCashflow(Cashflow cashflow){
        String id = cashflowRef.push().getKey();
        cashflow.setId(id);
        Map<String, Object> cashflowValue = new HashMap<>();
        cashflowValue.put("/" + id, cashflow.toMap());
        cashflowRef.updateChildren(cashflowValue);
    }

    public void updateCashflow(Cashflow cashflow){
        String id = cashflow.getId();
        Map<String, Object> cashflowValue = new HashMap<>();
        cashflowValue.put("/" + id, cashflow.toMap());
        cashflowRef.updateChildren(cashflowValue);
    }

    public void deleteCashflow(Cashflow cashflow){
        cashflowRef.child(cashflow.getId()).removeValue();
    }
}
