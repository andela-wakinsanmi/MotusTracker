package com.andela.motustracker.helper;

import android.content.Context;

/**
 * Created by Spykins on 05/04/16.
 */
public class AppContext {

    private static Context context;

    public AppContext(Context context) {
        AppContext.context = context;
    }

    public static Context get() {
        return context;
    }
}
