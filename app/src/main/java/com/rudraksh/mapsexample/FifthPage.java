package com.rudraksh.mapsexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.MobileAds;


/**
 * A simple {@link Fragment} subclass.
 */
public class FifthPage extends Fragment {


    public FifthPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fifth_page, container, false);

        MobileAds.initialize(getContext(),"[ADD ID]");

        view.findViewById(R.id.intotheApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),MapsActivity.class);
                intent.putExtra("ADS",1);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.previousFifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new FourthPage()).commit();
            }
        });

        return view;

    }

}
