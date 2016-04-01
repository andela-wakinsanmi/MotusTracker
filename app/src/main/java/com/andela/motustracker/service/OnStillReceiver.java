package com.andela.motustracker.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Spykins on 01/04/16.
 */
public class OnStillReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public  static void broadcastIntent(Context context) {
            Intent intent = new Intent();
            intent.setAction("com.andela.motustracker.CUSTOM_INTENT");
            context.sendBroadcast(intent);
    }
}
