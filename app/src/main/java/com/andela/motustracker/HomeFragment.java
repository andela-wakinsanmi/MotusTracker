package com.andela.motustracker;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andela.motustracker.service.MotusService;
import com.andela.motustracker.helper.OnHomeButtonClickListener;
import com.andela.motustracker.service.AddressReceiver;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    Button button;
    static TextView latitudeText;
    static TextView longitudeText;
    static TextView address;
    BroadcastReceiver receiver;
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
        latitudeText = (TextView)view.findViewById(R.id.id_userLatitudeText);
        longitudeText = (TextView)view.findViewById(R.id.id_userLongititudeText);
        address = (TextView)view.findViewById(R.id.id_userAddress);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeButtonClickListener.startButtonClicked(isButtonOnStart);
                if (isButtonOnStart) {
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
        AddressReceiver.broadcastIntent(getContext());
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


    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("waleola", "Home Fragment" );

        super.onCreate(savedInstanceState);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String address = intent.getStringExtra("address");
                double latitude = intent.getDoubleExtra("latitude", 1000);
                double longitude = intent.getDoubleExtra("longitude",1000);
                // do something here.
                Log.d("waleola", "Home Fragment Address = " + address);
                Log.d("waleola", "Home Fragment Latitude = " + latitude);
            }
        };
    }*/

    /*@Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter()
        );
    }
    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }*/

    public static class ReceiveBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("waleola","***********onReceive");
            latitudeText.setText(intent.getStringExtra("latitude"));
            longitudeText.setText(intent.getStringExtra("longitude"));
            address.setText(intent.getStringExtra("address"));
        }
    }
}
