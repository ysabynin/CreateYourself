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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.activity.TemplateDetails;
import com.kozsabynin.createyourself.adapter.TemplateListViewAdapter;
import com.kozsabynin.createyourself.domain.Template;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateFragment extends Fragment {
    private TemplateListViewAdapter adapter = null;
    private ListView listView;
    private Set<Template> baseItems = new HashSet<>();
    DatabaseReference templateRef = FirebaseDatabase.getInstance().getReference("template");

    public TemplateFragment() {
        // Required empty public constructor
    }


    private void initAdapter() {
        adapter = new TemplateListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this layouts.fragment
        View view = inflater.inflate(R.layout.template_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.template_list);

//        TemplateDbHelper templateDbHelper = new TemplateDbHelper(getActivity());
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        showDialog(fab);

        initAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Template template = (Template) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), TemplateDetails.class);
                intent.putExtra("template", template);
                startActivity(intent);
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

/*    @Override
    public void onResume() {

        TemplateDbHelper cashflowDbHelper = new TemplateDbHelper(getActivity());
        List<Template> baseItems = cashflowDbHelper.getTemplates();

        adapter = new TemplateListViewAdapter(getContext(), android.R.layout.simple_list_item_1, baseItems);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        super.onResume();
    }*/

}
