package com.rudraksh.mapsexample;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Intro_Activity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_);

        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer,new FirstPage()).commit();
    }
}
