package com.rudraksh.mapsexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPage extends Fragment {


    public FirstPage() {
        // Required empty public constructor
    }

private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first_page, container, false);
        textView=view.findViewById(R.id.welcomeTitle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                textView.animate().alpha(1).translationYBy(-900).setDuration(1500);
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new SecondPage()).commit();
                }
            }
        }).start();

        return view;
    }

}
