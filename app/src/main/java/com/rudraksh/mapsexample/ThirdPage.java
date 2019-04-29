package com.rudraksh.mapsexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdPage extends Fragment {


    public ThirdPage() {
        // Required empty public constructor
    }

    private TextView next,previous;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_third_page, container, false);

        next=view.findViewById(R.id.nextThird);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new FourthPage()).commit();
            }
        });

        previous=view.findViewById(R.id.previousThird);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new SecondPage()).commit();
            }
        });

        return view;
    }

}
