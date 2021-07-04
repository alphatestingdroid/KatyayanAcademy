package com.appsfeature.education.util;

import android.util.Log;

import com.appsfeature.education.BuildConfig;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class Logger {

    private static final String TAG = "@Logger";

    public static void log(String message) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
