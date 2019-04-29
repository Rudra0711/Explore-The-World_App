package com.rudraksh.mapsexample;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourthPage extends Fragment {


    public FourthPage() {
        // Required empty public constructor
    }
private ImageView imageView;
    private volatile int check=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_fourth_page, container, false);

        view.findViewById(R.id.nextFourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check==1) {
                    Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new FifthPage()).commit();
                }
                else{
                    Toast.makeText(getContext(),"Can't jump to the next step in between..!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.previousFourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intro_Activity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new ThirdPage()).commit();
            }
        });

        imageView=view.findViewById(R.id.withoutAddressFourth);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final ImageView handImageView= view.findViewById(R.id.handPointer);


                try {
                    handImageView.animate().alpha(1).setDuration(2000);
                    Thread.sleep(3000);
                    handImageView.setImageResource(R.drawable.handpointerpressed);
                    Thread.sleep(3000);


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handImageView.setVisibility(View.GONE);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            imageView.setImageResource(R.drawable.with_tappedmarker);
                            check=1;
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        return view;
    }


}
