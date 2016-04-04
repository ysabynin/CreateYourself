package com.kozsabynin.createyourself.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryActivityFragment extends Fragment {
    private TemplateListViewAdapter adapter = null;
    private ListView listView;

    public CategoryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        listView = (ListView) view.findViewById(R.id.category_list);

        CategoryDbHelper cashflowDbHelper = new CategoryDbHelper(getActivity());
        List<Category> baseItems = cashflowDbHelper.getCategories();

        CategoryListViewAdapter adapter = new CategoryListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/*                Category category = (Category) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CategoryDetailsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);*/

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        CategoryDbHelper cashflowDbHelper = new CategoryDbHelper(getActivity());
        List<Category> baseItems = cashflowDbHelper.getCategories();

        CategoryListViewAdapter adapter = new CategoryListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        super.onResume();
    }
}