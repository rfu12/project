package com.example.test;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EnterUnitFragment extends Fragment {
    View vEnterUnit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vEnterUnit = inflater.inflate(R.layout.fragment_enter_unit, container, false);
        return vEnterUnit;
    }
}