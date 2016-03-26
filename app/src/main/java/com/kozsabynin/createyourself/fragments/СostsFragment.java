package com.kozsabynin.createyourself.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.domain.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class СostsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cost_layout,null);

        ListView listView = (ListView) rootView.findViewById(R.id.cost_listview);

// определяем массив типа String
        final String[] titles = new String[] {
                "Подарок на 8-ое марта", "Шуба девушке", "Ужин в ресторане", "Бензин"
        };
// используем адаптер данных

        List<BaseItem> baseItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            baseItems.add(new BaseItem(titles[i],1000));
        }

        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(),android.R.layout.simple_list_item_1,baseItems);
        listView.setAdapter(adapter);
        return listView;
    }
}
