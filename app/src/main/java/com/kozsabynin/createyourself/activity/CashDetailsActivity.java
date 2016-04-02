package com.kozsabynin.createyourself.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.util.DateUtils;

import java.util.Calendar;
import java.util.Locale;

public class CashDetailsActivity extends AppCompatActivity {

    int DIALOG_DATE = 1;
    private Calendar curDate = Calendar.getInstance();
    int myYear;
    int myMonth;
    int myDay;


    private EditText dateEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        cashflow = (Cashflow) intent.getSerializableExtra("cashflow");

        final EditText costEditor = (EditText) findViewById(R.id.cost_editor);
        final EditText titleEditor = (EditText) findViewById(R.id.title_editor);
        dateEditor = (EditText) findViewById(R.id.date_input);

        final RadioButton incomeCheckBox = (RadioButton) findViewById(R.id.income_radio_button);

        Button saveButton = (Button) findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getApplicationContext());

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
                    cashflowDbHelper.updateCashflow(new Cashflow(cashflow.getId(), title, type, cost, curDate));
                else
                    cashflowDbHelper.insertCashflow(new Cashflow(title, type, cost, curDate));

                finish();
            }
        });


        if (cashflow.getId() != null) {
            costEditor.setText(String.valueOf(cashflow.getCost()));
            titleEditor.setText(cashflow.getTitle());

            incomeCheckBox.setChecked(CashType.INCOME == cashflow.getType());

            curDate = cashflow.getDate();

        } else {
            costEditor.setText("0.0");
            incomeCheckBox.setChecked(CashType.INCOME == cashflow.getType());

            curDate = Calendar.getInstance();
        }

        dateEditor.setText(DateUtils.getDateTextByCalendar(curDate));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void onclick(View view) {
        showDialog(DIALOG_DATE);
    }


    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack,
                    curDate.get(Calendar.YEAR),
                    curDate.get(Calendar.MONTH),
                    curDate.get(Calendar.DAY_OF_MONTH));

            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            curDate.set(myYear, myMonth, myDay);

            dateEditor.setText(DateUtils.getDateTextByCalendar(curDate));
        }
    };


    private Cashflow cashflow = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getApplicationContext());
            cashflowDbHelper.deleteCashflowById(cashflow);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.cash_details_activity, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}
