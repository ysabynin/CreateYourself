package com.kozsabynin.createyourself.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.BaseListViewAdapter;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.List;

public class TemplateActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.template_list);

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(this);
        List<Cashflow> baseItems = cashflowDbHelper.getCashflowTemplates();

        BaseListViewAdapter adapter = new BaseListViewAdapter(this, android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

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

        if (id == R.id.filter) {
/*            CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getApplicationContext());
            cashflowDbHelper.deleteCashflowById(cashflow);
            finish();*/
            return true;
        } else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_activity, menu);
        return true;
    }

}
