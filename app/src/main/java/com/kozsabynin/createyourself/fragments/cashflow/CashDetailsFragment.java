package com.kozsabynin.createyourself.fragments.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

/**
 * A placeholder fragment containing a simple view.
 */
public class CashDetailsFragment extends Fragment {

    public CashDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View details = inflater.inflate(R.layout.fragment_cash_details, container, false);

        Intent intent = getActivity().getIntent();
        Cashflow cashflow = (Cashflow) intent.getSerializableExtra("cashflow");

        EditText costEditor = (EditText)details.findViewById(R.id.cost_editor);
        EditText titleEditor = (EditText)details.findViewById(R.id.title_editor);
        RadioButton incomeCheckBox = (RadioButton)details.findViewById(R.id.income_radio_button);

        costEditor.setText(String.valueOf(cashflow.getCost()));
        titleEditor.setText(cashflow.getTitle());

        if(CashType.INCOME == cashflow.getType())
            incomeCheckBox.setChecked(true);
        else if(CashType.EXPENSE == cashflow.getType())
            incomeCheckBox.setChecked(false);

        return details;
    }
}
