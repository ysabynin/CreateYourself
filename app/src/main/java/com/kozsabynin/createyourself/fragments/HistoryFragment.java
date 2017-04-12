package com.kozsabynin.createyourself.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private CashflowListViewAdapter adapter = null;
    private ListView listView;
    List<Cashflow> baseItems = new ArrayList<>();
    DatabaseReference cashflowRef;
    ChildEventListener mChildEventListener = null;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.history_list);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();

        adapter = new CashflowListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
        cashflowRef = CashflowFirebaseService.getCashflowRef();

        cashflowRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                adapter.add(cashflow);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                adapter.remove(cashflow);
                adapter.add(cashflow);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Cashflow cashflow = dataSnapshot.getValue(Cashflow.class);
                adapter.remove(cashflow);
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

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history_activity, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
