package com.kozsabynin.createyourself.fragments.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

/**
 * A placeholder fragment containing a simple view.
 */
public class CashDetailsFragment extends Fragment {
    private Cashflow cashflow = null;

    public CashDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View details = inflater.inflate(R.layout.fragment_cash_details, container, false);

        setHasOptionsMenu(true);
        Intent intent = getActivity().getIntent();

        cashflow = (Cashflow) intent.getSerializableExtra("cashflow");

        final EditText costEditor = (EditText) details.findViewById(R.id.cost_editor);
        final EditText titleEditor = (EditText) details.findViewById(R.id.title_editor);
        final RadioButton incomeCheckBox = (RadioButton) details.findViewById(R.id.income_radio_button);

        Button saveButton = (Button) details.findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getContext());

                String title = titleEditor.getText().toString();

                CashType type = (incomeCheckBox.isChecked()) ? CashType.INCOME : CashType.EXPENSE;

                String costLine = costEditor.getText().toString().split(" ")[0].replace(",", ".");
                Double cost = null;
                try {
                    cost = Double.valueOf(costLine);
                } catch (NumberFormatException e) {
                    cost = 0.0;
                }

                if (cashflow.getId() != null)
                    cashflowDbHelper.updateCashflow(new Cashflow(cashflow.getId(), title, type, cost));
                else
                    cashflowDbHelper.insertCashflow(new Cashflow(title, type, cost));

                getActivity().finish();
            }
        });

        if(cashflow.getId() != null){
            costEditor.setText(String.valueOf(cashflow.getCost()));
            titleEditor.setText(cashflow.getTitle());
            incomeCheckBox.setChecked(CashType.INCOME == cashflow.getType());
        } else {
            costEditor.setText("0.0");
            incomeCheckBox.setChecked(CashType.INCOME == cashflow.getType());
        }

        return details;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cash_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getContext());
            cashflowDbHelper.deleteCashflowById(cashflow);
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
