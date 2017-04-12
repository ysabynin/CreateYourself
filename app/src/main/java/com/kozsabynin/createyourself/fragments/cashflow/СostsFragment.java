package com.kozsabynin.createyourself.fragments.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.activity.CashDetailsActivity;
import com.kozsabynin.createyourself.adapter.CashflowListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowFirebaseService;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.ArrayList;
import java.util.List;

public class СostsFragment extends Fragment {
    private ListView listView;
    CashflowListViewAdapter adapter = null;
    DatabaseReference cashflowRef;
    ChildEventListener mChildEventListener = null;

    List<Cashflow> baseItems = new ArrayList<>();

    private void initAdapter(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashflow_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.cashflow_list);

        adapter = new CashflowListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        this.listView.setAdapter(adapter);
        cashflowRef = CashflowFirebaseService.getCashflowRef();
        mChildEventListener = cashflowRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if (cashflow.getType().equals("E")) {
                    adapter.add(cashflow);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if (cashflow.getType().equals("E")) {
                    adapter.remove(cashflow);
                    adapter.add(cashflow);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if (cashflow.getType().equals("E")) {
                    adapter.remove(cashflow);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cashflow cashflow = (Cashflow) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CashDetailsActivity.class);
                intent.putExtra("cashflow", cashflow);
                startActivity(intent);
            }
        });
        return listView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mChildEventListener != null){
            cashflowRef.removeEventListener(mChildEventListener);
        }

        adapter.clear();
    }
}
