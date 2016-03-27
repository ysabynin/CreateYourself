package com.kozsabynin.createyourself.fragments.cashflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.List;

public class СostsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashflow_list,null);

        ListView listView = (ListView) rootView.findViewById(R.id.cashflow_list);

// определяем массив типа String
        final String[] titles = new String[] {
                "Подарок на 8-ое марта", "Шуба девушке", "Ужин в ресторане", "Бензин"
        };
// используем адаптер данных

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<Cashflow> baseItems = cashflowDbHelper.getCashflowByType(CashType.EXPENSE);

        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(),android.R.layout.simple_list_item_1,baseItems);
        listView.setAdapter(adapter);
        return listView;
    }
}
