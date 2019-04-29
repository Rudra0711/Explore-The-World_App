package com.rudraksh.mapsexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondPage extends Fragment {


    public SecondPage() {
        // Required empty public constructor
    }

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_second_page, container, false);

        textView=view.findViewById(R.id.getStarted);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new ThirdPage()).commit();
            }
        });

        return view;
    }

}
