package com.kozsabynin.createyourself.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.CashflowFirebaseService;
import com.kozsabynin.createyourself.db.TemplateFirebaseService;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.domain.Category;
import com.kozsabynin.createyourself.domain.Template;
import com.kozsabynin.createyourself.util.DateUtils;

import java.util.Calendar;
import java.util.Locale;

public class CashDetailsActivity extends AppCompatActivity {

    int DIALOG_DATE = 1;
    private Calendar curDate = Calendar.getInstance();
    int myYear;
    int myMonth;
    int myDay;

    private Context context;

    private EditText dateEditor;
    private EditText costEditor;
    private EditText categoryEditor;
    private EditText titleEditor;
    private RadioButton incomeCheckBox;
    private CheckBox templateCheckBox;

    private Category category;
    private Template template;

    DatabaseReference cashflowRef = FirebaseDatabase.getInstance().getReference("cashflow");
    DatabaseReference templateRef = FirebaseDatabase.getInstance().getReference("template");

    CashflowFirebaseService cashflowFirebaseService = new CashflowFirebaseService();
    TemplateFirebaseService templateFirebaseService = new TemplateFirebaseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        cashflow = (Cashflow) intent.getSerializableExtra("cashflow");

        costEditor = (EditText) findViewById(R.id.cost_editor);
        titleEditor = (EditText) findViewById(R.id.title_editor);
        dateEditor = (EditText) findViewById(R.id.date_input);
        categoryEditor = (EditText) findViewById(R.id.category_editor);

        incomeCheckBox = (RadioButton) findViewById(R.id.category_income_radio_button);
        templateCheckBox = (CheckBox) findViewById(R.id.template_ind);

        bindSaveButtonEvent();
        initFields(intent);

        bindCategoryOnClickEvent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindCategoryOnClickEvent() {
        final Intent intent = new Intent(this, CategoryActivity.class);

        categoryEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("cashflow", cashflow);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void bindSaveButtonEvent() {
        Button saveButton = (Button) findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditor.getText().toString();
                String categoryText = categoryEditor.getText().toString();
                String costText = costEditor.getText().toString();
                if (title.matches("") || categoryText.matches("") || costText.matches("")) {
                    Toast.makeText(getApplicationContext(), "Одно из полей пустое", Toast.LENGTH_LONG).show();
                    return;
                }

                CashType type = (incomeCheckBox.isChecked()) ? CashType.INCOME : CashType.EXPENSE;
                String costLine = costEditor.getText().toString().split(" ")[0].replace(",", ".");
                boolean isTemplate = (templateCheckBox.isChecked()) ? true : false;

                Double cost = null;

                try {
                    cost = Double.valueOf(costLine);
                } catch (NumberFormatException e) {
                    cost = 0.0;
                }

                if(category == null)
                    category = cashflow.getCategory();

                if (isTemplate) {
                    Template sendTemplate = new Template(null, title, type, category, cost);
                    templateFirebaseService.insertTemplate(sendTemplate);
                }

                if (cashflow.getId() != null){
                    String id = cashflow.getId();
                    Cashflow sendCashflow = new Cashflow(id, title, type,cost, curDate,category);
                    cashflowFirebaseService.updateCashflow(sendCashflow);
                }
                else {
                    Cashflow sendCashflow = new Cashflow(null, title, type, cost, curDate, category);
                    cashflowFirebaseService.insertCashflow(sendCashflow);
                }
                finish();
            }
        });
    }

    public void initFields(Intent intent) {
        if (cashflow.getId() != null) {
            costEditor.setText(String.valueOf(cashflow.getCost()));
            titleEditor.setText(cashflow.getTitle());
            categoryEditor.setText(cashflow.getCategory().getTitle());

            incomeCheckBox.setChecked(CashType.INCOME.getText() == cashflow.getType());
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTimeInMillis(cashflow.getDate());
            curDate = tempCalendar;

        } else if (intent.hasExtra("isFromTemplateActivity")) {
            cashflow.setId(null);

            costEditor.setText(String.valueOf(cashflow.getCost()));
            titleEditor.setText(cashflow.getTitle());

            incomeCheckBox.setChecked(CashType.INCOME.getText() == cashflow.getType());
            curDate = Calendar.getInstance();
        } else {
            costEditor.setText("0.0");
            incomeCheckBox.setChecked(CashType.INCOME.getText() == cashflow.getType());

            curDate = Calendar.getInstance();
        }

        dateEditor.setText(DateUtils.getDateTextByCalendar(curDate));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(data != null && data.hasExtra("category")){
                category = (Category) data.getSerializableExtra("category");
                categoryEditor.setText(category.getTitle());
            }
        }
        else if(requestCode == 2){
            if(data != null && data.hasExtra("template")){
                template = (Template) data.getSerializableExtra("template");
                category = template.getCategory();

                costEditor.setText(String.valueOf(template.getCost()));
                titleEditor.setText(template.getTitle());
                categoryEditor.setText(template.getCategory().getTitle());
                incomeCheckBox.setChecked(CashType.INCOME.getText() == template.getType());
            }
        }
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
            cashflowFirebaseService.deleteCashflow(cashflow);
            finish();
            return true;
        } else if (id == R.id.templates) {
            Intent intent = new Intent(this, TemplateActivity.class);
            startActivityForResult(intent, 2);
            return true;
        } else if (id == android.R.id.home) {
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
