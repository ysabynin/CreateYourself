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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

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

/*        final String[] titles = new String[]{
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата",
                "Выиграл в лотерею", "Фриланс", "Халтура от друзей", "Зарплата"
        };
        for (int i = 0; i < titles.length; i++) {
            baseItems.add(new Cashflow(titles[i], CashType.INCOME,1000.0));
        }*/

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<Cashflow> baseItems = cashflowDbHelper.getCashflowByType(CashType.INCOME);

        BaseListViewAdapter adapter = new BaseListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
        return listView;
    }

    private void showDialog(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.create_cashflow);

                final EditText costEditor = (EditText)dialog.findViewById(R.id.cost_editor);
                final EditText titleEditor = (EditText)dialog.findViewById(R.id.title_editor);
                final RadioButton incomeCheckBox = (RadioButton)dialog.findViewById(R.id.income_radio_button);
                RadioGroup radiogroup = (RadioGroup) dialog.findViewById(R.id.cash_type_radio_group);


                dialog.setTitle("Новая операция");
                dialog.setCancelable(true);
//                dialog.setContentView(v);

                Button saveButton = (Button)dialog.findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getContext());

                        String title = titleEditor.getText().toString();
                        System.out.println(title);

                        CashType type = (incomeCheckBox.isChecked())?CashType.INCOME:CashType.EXPENSE;
                        System.out.println(type);

                        String costLine = costEditor.getText().toString().split(" ")[0].replace(",",".");
                    Double cost = null;
                        try {
                            cost = Double.valueOf(costLine);
                        } catch (NumberFormatException e) {
                            cost = 0.0;
                        }
                        System.out.println(cost);


                        cashflowDbHelper.insertCashflow(new Cashflow(title,type,cost));
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}

