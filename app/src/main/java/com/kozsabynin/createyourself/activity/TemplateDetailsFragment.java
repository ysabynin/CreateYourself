package com.kozsabynin.createyourself.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kozsabynin.createyourself.R;

/**
 * A placeholder layouts.fragment containing a simple view.
 */
public class TemplateDetailsFragment extends Fragment {

    public TemplateDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_details, container, false);
    }
}
