package com.example.runningtrial.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningtrial.R;
import com.example.runningtrial.base.FragmentBasic;

public class ParentMainFragment extends FragmentBasic {

    public ParentMainFragment() {  // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParentMainFragment.
     */
    public static ParentMainFragment newInstance() {
        ParentMainFragment fragment = new ParentMainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_parent_main, container, false);
        return rootView;
    }

}
