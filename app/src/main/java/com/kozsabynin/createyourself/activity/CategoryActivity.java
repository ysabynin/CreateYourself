package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.CategoryListViewAdapter;
import com.kozsabynin.createyourself.adapter.TemplateListViewAdapter;
import com.kozsabynin.createyourself.db.CategoryDbHelper;
import com.kozsabynin.createyourself.db.TemplateDbHelper;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.domain.Category;
import com.kozsabynin.createyourself.domain.Template;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ListView listView;
    private Cashflow cashflowFromDetailsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.category_list);
        cashflowFromDetailsActivity = (Cashflow) getIntent().getSerializableExtra("cashflow");

        CategoryDbHelper cashflowDbHelper = new CategoryDbHelper(this);
        List<Category> baseItems = cashflowDbHelper.getCategories();

        CategoryListViewAdapter adapter = new CategoryListViewAdapter(this, android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cashflow template = (Cashflow) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CashDetailsActivity.class);

                if (cashflowFromDetailsActivity.getId() == null)
                    intent.putExtra("template", template);
                intent.putExtra("isFromTemplateActivity", true);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
