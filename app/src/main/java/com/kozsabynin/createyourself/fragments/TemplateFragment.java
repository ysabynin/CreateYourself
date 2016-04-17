package com.kozsabynin.createyourself.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.activity.TemplateDetails;
import com.kozsabynin.createyourself.adapter.TemplateListViewAdapter;
import com.kozsabynin.createyourself.db.TemplateDbHelper;
import com.kozsabynin.createyourself.domain.Template;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateFragment extends Fragment {


    public TemplateFragment() {
        // Required empty public constructor
    }

    private TemplateListViewAdapter adapter = null;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this layouts.fragment
        View view = inflater.inflate(R.layout.template_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.template_list);

        TemplateDbHelper templateDbHelper = new TemplateDbHelper(getActivity());
        List<Template> baseItems = templateDbHelper.getTemplates();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        adapter = new TemplateListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Template template = (Template) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), TemplateDetails.class);
                intent.putExtra("template", template);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(false);

        return view;
    }

    private void showDialog(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TemplateDetails.class);
                intent.putExtra("template", new Template());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {

        TemplateDbHelper cashflowDbHelper = new TemplateDbHelper(getActivity());
        List<Template> baseItems = cashflowDbHelper.getTemplates();

        adapter = new TemplateListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        super.onResume();
    }

}
