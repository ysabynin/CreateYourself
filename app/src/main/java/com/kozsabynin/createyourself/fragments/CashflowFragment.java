package com.kozsabynin.createyourself.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kozsabynin.createyourself.R;

/**
 * Created by Evgeni Developer on 28.02.2016.
 */
public class CashflowFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*
        return super.onCreateView(inflater, container, savedInstanceState);
*/
        View rootView = inflater.inflate(R.layout.fragment_cashflow,container,false);

        return rootView;
    }
}
