package com.kozsabynin.createyourself.fragments.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.activity.CashDetailsActivity;
import com.kozsabynin.createyourself.adapter.CashflowListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.db.CashflowFirebaseService;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.HashSet;
import java.util.Set;

public class EarningsFragment extends Fragment {
    private CashflowListViewAdapter adapter = null;
    private ListView listView = null;
    Set<Cashflow> baseItems = new HashSet<>();

    DatabaseReference cashflowRef = FirebaseDatabase.getInstance().getReference("cashflow");

    private void initAdapter(){
        adapter = new CashflowListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashflow_list, null);

        listView = (ListView) rootView.findViewById(R.id.cashflow_list);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
//        baseItems = cashflowDbHelper.getCashflow(CashType.INCOME);

        initAdapter();

        cashflowRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if(cashflow.getType().equals("I")) {
                    baseItems.add(cashflow);
                    initAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if(cashflow.getType().equals("I")) {
                    baseItems.remove(cashflow);
                    baseItems.add(cashflow);
                    initAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                if(cashflow.getType().equals("I")){
                    baseItems.remove(cashflow);
                    initAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void showDialog(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CashDetailsActivity.class);
                intent.putExtra("cashflow", new Cashflow());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {

/*        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<Cashflow> baseItems = cashflowDbHelper.getCashflow(CashType.INCOME);*/

        initAdapter();
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}

