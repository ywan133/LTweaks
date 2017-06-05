package li.lingfeng.ltweaks.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

    // https://stackoverflow.com/questions/6559520/print-a-view-hierarchy-on-a-device
    public static void printViewHierarchy(ViewGroup vg, String prefix) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            String desc = prefix + " | " + "[" + i + "/" + (vg.getChildCount()-1) + "] "+ v.getClass().getSimpleName() + " " + v.getId();
            Log.v("x", desc);

            if (v instanceof ViewGroup) {
                printViewHierarchy((ViewGroup)v, desc);
            }
        }
    }
}
