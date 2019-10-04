package com.pickle.ourgames.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pickle.ourgames.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularRootFragment extends Fragment {


    public PopularRootFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentManager fm = getFragmentManager();

       fm.beginTransaction().replace(R.id.containerView, new PopularFragment()).addToBackStack(null).commit();

      // fm.beginTransaction().replace(R.id.fragment_container, new PopularFragment()).addToBackStack(null).commit();
return inflater.inflate(R.layout.fragment_tab_holder, container, false);
       // return inflater.inflate(R.layout.fragment_popular_root, container, false);
    }

}
