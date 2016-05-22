package com.example.administrator.getpet.ui.Home.SendAdopt.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.getpet.R;

/**
 * Created by Koreleone on 2016-05-20.
 */
public class selectpet_fragment extends Fragment {

    public selectpet_fragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selectpet, container, false);
    }
}
