package com.example.root.hospitalsnearyou.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.hospitalsnearyou.R;

public class HospitalNameList extends Fragment {
    public  HospitalNameList()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hospital_name_list, container, false);

        return rootView;
    }

}
