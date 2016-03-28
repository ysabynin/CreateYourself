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

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.activity.CashDetailsActivity;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.List;

public class EarningsFragment extends Fragment {
    private BaseListViewAdapter adapter = null;
    private ListView listView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashflow_list, null);

        listView = (ListView) rootView.findViewById(R.id.cashflow_list);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<Cashflow> baseItems = cashflowDbHelper.getCashflowByType(CashType.INCOME);

        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

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

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<Cashflow> baseItems = cashflowDbHelper.getCashflowByType(CashType.INCOME);

        adapter = new BaseListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        super.onResume();
    }
}

