package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.TemplateListViewAdapter;
import com.kozsabynin.createyourself.db.TemplateDbHelper;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.domain.Template;

import java.util.HashSet;
import java.util.Set;

public class TemplateActivity extends AppCompatActivity {
    private TemplateListViewAdapter adapter = null;
    private ListView listView;
    private Cashflow cashflowFromDetailsActivity;
    private Set<Template> baseItems = new HashSet<>();
    DatabaseReference templateRef = FirebaseDatabase.getInstance().getReference("template");

    private void initAdapter() {
        adapter = new TemplateListViewAdapter(this, android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.template_list);

        TemplateDbHelper cashflowDbHelper = new TemplateDbHelper(this);
//        baseItems = cashflowDbHelper.getTemplates();

        initAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Template template = (Template) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CashDetailsActivity.class);

                intent.putExtra("template", template);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        templateRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Template template = dataSnapshot.getValue(Template.class);
                        baseItems.add(template);
                        initAdapter();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Template template = dataSnapshot.getValue(Template.class);
                        baseItems.remove(template);
                        baseItems.add(template);
                        initAdapter();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Template template = dataSnapshot.getValue(Template.class);
                        baseItems.remove(template);
                        initAdapter();
                        adapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }

        );

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
