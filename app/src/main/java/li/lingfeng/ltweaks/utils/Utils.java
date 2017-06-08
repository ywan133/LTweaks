package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by smallville on 2017/3/29.
 */

public class Utils {

    public static String stringJoin(Collection<String> collection) {
        StringBuilder builder = new StringBuilder();
        for (String s : collection) {
            if (collection.size() > 0)
                builder.append('\n');
            builder.append(s);
        }
        return builder.toString();
    }






    // https://stackoverflow.com/questions/14098963/how-to-perform-ontouch-event-from-code
    public static void performTouchOn(View target, View parent, Activity act){
        int[] coords = new int[2];
        target.getLocationOnScreen(coords);
        int x = coords[0];
        int y = coords[1];


        // Obtain MotionEvent object
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;


        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.TOOL_TYPE_FINGER,
                x,
                y,
                metaState
        );
        // Dispatch touch event to view
        if(null != target){
            target.dispatchTouchEvent(motionEvent);
        }
        if(null != parent){
            parent.dispatchTouchEvent(motionEvent);
        }
        if(null != act) {
            act.dispatchTouchEvent(motionEvent);
        }


    }

}
