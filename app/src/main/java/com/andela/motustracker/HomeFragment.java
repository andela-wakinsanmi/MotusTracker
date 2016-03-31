package com.andela.motustracker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andela.motustracker.helper.MotusService;
import com.andela.motustracker.helper.OnHomeButtonClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Button button;
    boolean isButtonOnStart = true;
    OnHomeButtonClickListener onHomeButtonClickListener;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeButtonClickListener.startButtonClicked(isButtonOnStart);
                if(isButtonOnStart) {
                    button.setText("Stop Tracking");
                    startService();
                    isButtonOnStart = false;
                } else {
                    button.setText("Start Tracking");
                    stopService();
                    isButtonOnStart = true;
                }
            }
        });

        return view ;

    }

    public void startService() {
        Intent intent = new Intent(getActivity(),MotusService.class);
        getActivity().startService(intent);
    }

    public void stopService() {
        Intent intent = new Intent(getActivity(),MotusService.class);
        getActivity().stopService(intent);

    }

    @Override
    public void onAttach(Context context) {
        try{
            onHomeButtonClickListener = (OnHomeButtonClickListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onAttach(context);
    }
}
