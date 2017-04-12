package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.TemplateFirebaseService;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Category;
import com.kozsabynin.createyourself.domain.Template;

public class TemplateDetails extends AppCompatActivity {
    private EditText costEditor;
    private EditText categoryEditor;
    private EditText titleEditor;
    private RadioButton incomeCheckBox;
    private Button saveButton;

    private Category category;
    private Template template;
    TemplateFirebaseService templateFirebaseService = new TemplateFirebaseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        template = (Template) intent.getSerializableExtra("template");

        costEditor = (EditText) findViewById(R.id.template_cost);
        titleEditor = (EditText) findViewById(R.id.template_title);
        categoryEditor = (EditText) findViewById(R.id.tempalate_category);
        saveButton = (Button) findViewById(R.id.save_template);

        incomeCheckBox = (RadioButton) findViewById(R.id.category_income_type);

        bindSaveButtonEvent();
        initFields(intent);

        bindCategoryOnClickEvent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null && data.hasExtra("category")) {
                category = (Category) data.getSerializableExtra("category");
                categoryEditor.setText(category.getTitle());
            }
        }
    }

    private void bindCategoryOnClickEvent() {
        final Intent intent = new Intent(this, CategoryActivity.class);

        categoryEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);
            }
        });

    }

    private void bindSaveButtonEvent() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditor.getText().toString();
                CashType type = (incomeCheckBox.isChecked()) ? CashType.INCOME : CashType.EXPENSE;
                String costLine = costEditor.getText().toString().split(" ")[0].replace(",", ".");

                String categoryText = categoryEditor.getText().toString();
                String costText = costEditor.getText().toString();

                if (title.matches("") || categoryText.matches("") || costText.matches("")) {
                    Toast.makeText(getApplicationContext(), "Одно из полей пустое", Toast.LENGTH_LONG).show();
                    return;
                }

                Double cost;

                try {
                    cost = Double.valueOf(costLine);
                } catch (NumberFormatException e) {
                    cost = 0.0;
                }

                if (category == null)
                    category = template.getCategory();

                if (template != null && template.getId() != null) {
                    String id = template.getId();
                    Template sendTemplate = new Template(id, title, type, category, cost);
                    templateFirebaseService.updateTemplate(sendTemplate);
                } else {
                    Template sendTemplate = new Template(null, title, type, category, cost);
                    templateFirebaseService.insertTemplate(sendTemplate);
                }
                finish();
            }
        });
    }

    public void initFields(Intent intent) {
        if (template != null && template.getId() != null) {
            costEditor.setText(String.valueOf(template.getCost()));
            titleEditor.setText(template.getTitle());
            categoryEditor.setText(template.getCategory().getTitle());
            incomeCheckBox.setChecked(CashType.INCOME.getText() == template.getType());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            templateFirebaseService.deleteTemplate(template);
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
        getMenuInflater().inflate(R.menu.menu_template_details, menu);
        return super.onPrepareOptionsMenu(menu);
    }

}
