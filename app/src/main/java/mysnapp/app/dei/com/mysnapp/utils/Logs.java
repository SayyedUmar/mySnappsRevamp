package mysnapp.app.dei.com.mysnapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import mysnapp.app.dei.com.mysnapp.BuildConfig;

/**
 * The generic log class for application wide logging
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public class Logs {
    private static final boolean ENABLE_LOGS = BuildConfig.DEBUG;

    private Logs() {
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, Exception e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, OutOfMemoryError e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static boolean getIsLogsEnabled() {
        return ENABLE_LOGS;
    }

    @SuppressWarnings("unused")
    public static void reportException(Exception e) {
        if (ENABLE_LOGS) {
            Log.e("Exception", e.toString(), e);
        }
    }

    public static void e(String tag, String msg) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg);
        }
    }

    public static void shortToast(Context context, String msg) {
        if (ENABLE_LOGS) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void longToast(Context context, String msg) {
        if (ENABLE_LOGS) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

}
