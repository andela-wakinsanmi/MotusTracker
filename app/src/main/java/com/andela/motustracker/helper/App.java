package com.andela.motustracker.helper;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

/**
 * Created by Spykins on 02/04/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new AppContext(this);
    }

}
