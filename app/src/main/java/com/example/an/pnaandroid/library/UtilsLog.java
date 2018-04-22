package com.example.an.pnaandroid.library;

import android.util.Log;
import com.example.an.pnaandroid.BuildConfig;

public class UtilsLog {
    public static final boolean DEBUG_ON = true;
    private static final String TAG = "TN_LOG";

    public static void l(int mode, String message) {
        if (DEBUG_ON) {
            switch (mode) {
                case 4:
                    Log.e(TAG, message);
                    break;
                case 3:
                    Log.w(TAG, message);
                    break;
                case 2:
                    Log.i(TAG, message);
                    break;
                case 1:
                    Log.d(TAG, message);
                    break;
                default:
                    Log.v(TAG, message);
                    break;
            }
        }
    }

    public static void l(Exception e) {
        if (DEBUG_ON) {
            if (e != null && e.getMessage() != null)
                Log.e(TAG, e.getMessage());
        }
    }

    public static void l(Class<?> className, Exception e) {
        if (DEBUG_ON) {
            if (e != null && e.getMessage() != null)
                Log.e(TAG, className.getSimpleName() + " -- " + e.getMessage());
        }
    }

    public static void logEx(Exception ex, Class<?> className) {
        if (BuildConfig.DEBUG) {
            Log.e("Exception|" + className.getSimpleName(), ex.getMessage() + " -");
        }
    }


    public static void logPNA(String title, Object message, Class<?> className) {
        if (BuildConfig.DEBUG) {
            Log.e("PNA|" + className.getSimpleName() + " -- " + title, message + " -");
        }
    }
}
