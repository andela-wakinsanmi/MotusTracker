package com.andela.motustracker.helper;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

/**
 * Created by Spykins on 02/04/16.
 */
public class App extends Application implements TextToSpeech.OnInitListener {
    private static Context context;
    @Override
    public void onInit(int status) {

    }

    public static Context getContext(){
        return context;
    }

    public static void setContext(Context mContext) {
        context = mContext;
    }
}
