package com.kozsabynin.createyourself.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.domain.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class EarningsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.earnings_layout,null);

        ListView listView = (ListView) rootView.findViewById(R.id.income_listview);

        final String[] titles = new String[] {
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата"
        };

        List<BaseItem> baseItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            baseItems.add(new BaseItem(titles[i],1000));
        }
        
        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(),android.R.layout.simple_list_item_1,baseItems);
        listView.setAdapter(adapter);
        return listView;
    }
}

