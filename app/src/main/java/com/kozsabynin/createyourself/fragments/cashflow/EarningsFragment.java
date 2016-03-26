package com.kozsabynin.createyourself.fragments.cashflow;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.domain.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class EarningsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashflow_list, null);

        ListView listView = (ListView) rootView.findViewById(R.id.cashflow_list);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        final String[] titles = new String[]{
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата"
        };

        List<BaseItem> baseItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            baseItems.add(new BaseItem(titles[i], 1000));
        }

        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
        return listView;
    }

    private void showDialog(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = getActivity().getLayoutInflater().inflate(R.layout.create_cashflow, null);

                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle("Новая операция");
                dialog.setCancelable(true);
                dialog.setContentView(v);

                Button saveButton = (Button)v.findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}

