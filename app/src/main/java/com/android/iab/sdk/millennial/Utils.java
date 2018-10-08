/*
 * Copyright (c) 2015. Millennial Media. All rights reserved.
 */

package com.android.iab.sdk.millennial;

import android.os.Handler;
import android.os.Looper;


public class Utils {

    private static Handler mainHandler;


    static {
        mainHandler = new Handler(Looper.getMainLooper());
    }


    public static void runOnUiThread(Runnable runnable) {

        if (isUiThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }


    public static boolean isUiThread() {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            return true;
        }

        return false;
    }
}
