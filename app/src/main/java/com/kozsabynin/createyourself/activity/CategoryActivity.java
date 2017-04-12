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
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.CategoryListViewAdapter;
import com.kozsabynin.createyourself.db.CategoryFirebaseService;
import com.kozsabynin.createyourself.domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ListView listView;
    CategoryListViewAdapter adapter;
    List<Category> baseItems = new ArrayList<>();

    DatabaseReference categoryRef = null;
    ChildEventListener mChildEventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.category_list);
        adapter = new CategoryListViewAdapter(this, android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CashDetailsActivity.class);

                intent.putExtra("category", category);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        categoryRef = CategoryFirebaseService.getCategoryRef();
        mChildEventListener = categoryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Category category = dataSnapshot.getValue(Category.class);
                adapter.add(category);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Category category = dataSnapshot.getValue(Category.class);
                adapter.remove(category);
                adapter.add(category);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);
                adapter.remove(category);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChildEventListener != null)
            categoryRef.removeEventListener(mChildEventListener);
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
