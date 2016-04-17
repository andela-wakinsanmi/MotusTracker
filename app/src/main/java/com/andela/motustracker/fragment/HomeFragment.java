package com.andela.motustracker.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andela.motustracker.R;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.service.MotusService;
import com.andela.motustracker.helper.OnHomeButtonClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    Button button;
    static TextView addressTextView;
    static TextView activityDetected;
    static TextView timeTextView;

    BroadcastReceiver receiver;
    OnHomeButtonClickListener onHomeButtonClickListener;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        timeTextView = (TextView)view.findViewById(R.id.id_countDownText);
        activityDetected = (TextView) view.findViewById(R.id.activityDetected);

        button = (Button) view.findViewById(R.id.button);
        String buttonText = isButtonTracking() ? "Stop Tracking" : "Start Tracking";
        int colour = isButtonTracking() ? Color.RED : Color.parseColor("#43AC45");
        button.setText(buttonText);
        button.setBackgroundColor(colour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonTracking()) {
                    button.setText("Start Tracking");
                    button.setBackgroundColor(Color.parseColor("#43AC45"));
                    stopService();
                    saveButtonState(false);
                } else {
                    button.setText("Stop Tracking");
                    button.setBackgroundColor(Color.RED);
                    startService();
                    saveButtonState(true);
                }
            }
        });

        return view ;

    }

    private void saveButtonState(boolean flag) {
        SharedPreferences sharedPreferences = AppContext.get().
                getSharedPreferences(getString
                (R.string.BUTTON_STATUS), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.button_flag), flag);
        editor.commit();

    }

    private boolean isButtonTracking() {
        SharedPreferences sharedPreferences = AppContext.get().
                getSharedPreferences(getString
                (R.string.BUTTON_STATUS), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.button_flag),false);
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

    public static class ReceiveBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (addressTextView != null) {
                addressTextView.setText(intent.getStringExtra("address"));
            }
        }
    }

    public static class ReceiveDetectedActivtyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("activityDetected");
            if(activityDetected != null) {
                activityDetected.setText(status);
            }
        }
    }

    public static class ReceiveTimerBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String time = intent.getStringExtra("time");
            if (timeTextView != null) {
                timeTextView.setText(time);
            }
        }
    }
}
