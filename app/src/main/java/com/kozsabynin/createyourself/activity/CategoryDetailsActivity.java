package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.CategoryDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Category;

public class CategoryDetailsActivity extends AppCompatActivity {

    private EditText titleEditor;
    private EditText colorEditor;
    private RadioButton incomeCheckBox;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");

        titleEditor = (EditText) findViewById(R.id.category_name);
        incomeCheckBox = (RadioButton) findViewById(R.id.category_income_radio_button);

        bindSaveButtonEvent();
        initFields(intent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void bindSaveButtonEvent() {
        Button saveButton = (Button) findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());

                String title = titleEditor.getText().toString();
                CashType type = (incomeCheckBox.isChecked()) ? CashType.INCOME : CashType.EXPENSE;

                if (category.getId() != null)
                    categoryDbHelper.updateCategory(new Category(category.getId(), title, type));
                else
                    categoryDbHelper.insertCategory(new Category(title, type));

                finish();
            }
        });
    }

    public void initFields(Intent intent) {
        if (category.getId() != null) {
            titleEditor.setText(category.getTitle());
            incomeCheckBox.setChecked(CashType.INCOME == category.getType());
        } else {
            incomeCheckBox.setChecked(CashType.INCOME == category.getType());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());
            categoryDbHelper.deleteCategory(category);
            finish();
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
        getMenuInflater().inflate(R.menu.category_details, menu);
        return super.onPrepareOptionsMenu(menu);
    }

}
