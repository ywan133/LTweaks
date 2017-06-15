package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import li.lingfeng.ltweaks.BuildConfig;

/**
 * Created by smallville on 2016/11/23.
 */
public class Logger {
    private final static String TAG = "WY: Xposed";

    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void stackTrace(Throwable e) {
        Log.e(TAG, Log.getStackTraceString(e));
    }

    public static void toast_i(Activity activity, String s){
        i(s);
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }
    public static void toast_i_long(Activity activity, String s){
        i(s);
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
    }
}
