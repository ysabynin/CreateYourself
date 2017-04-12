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
import com.kozsabynin.createyourself.db.TemplateFirebaseService;
import com.kozsabynin.createyourself.domain.Template;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateFragment extends Fragment {
    private TemplateListViewAdapter adapter = null;
    private ListView listView;
    private List<Template> baseItems = new ArrayList<>();

    public TemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.template_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.template_list);

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

        TemplateFirebaseService.getTemplateRef().addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Template template = dataSnapshot.getValue(Template.class);
                        adapter.add(template);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Template template = dataSnapshot.getValue(Template.class);
                        adapter.remove(template);
                        adapter.add(template);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Template template = dataSnapshot.getValue(Template.class);
                        adapter.remove(template);
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
}
