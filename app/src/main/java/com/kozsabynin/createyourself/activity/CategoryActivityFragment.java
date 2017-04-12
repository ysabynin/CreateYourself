package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.adapter.CategoryListViewAdapter;
import com.kozsabynin.createyourself.db.CategoryFirebaseService;
import com.kozsabynin.createyourself.domain.Category;

import java.util.HashSet;
import java.util.Set;

/**
 * A placeholder layouts.fragment containing a simple view.
 */
public class CategoryActivityFragment extends Fragment {
    ListView listView;
    CategoryListViewAdapter adapter;
    Set<Category> baseItems = new HashSet<>();

    DatabaseReference categoryRef = null;
    ChildEventListener mChildEventListener = null;

    public CategoryActivityFragment() {
    }

    private void initAdapter(){
        adapter = new CategoryListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        listView = (ListView) view.findViewById(R.id.category_list);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        initAdapter();

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CategoryDetailsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mChildEventListener != null)
            categoryRef.removeEventListener(mChildEventListener);
    }

    private void showDialog(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CategoryDetailsActivity.class);
                intent.putExtra("category", new Category());
                startActivity(intent);
            }
        });
    }
}