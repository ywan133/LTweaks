package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

    public static void printMsg2ExportedActivity(Activity act, String msg){
        Intent intent = new Intent();
        intent.setClassName(
                // Your app's package name
                "li.lingfeng.ltweaks",
                // The full class name of the activity you want to start
                "li.lingfeng.ltweaks.activities.WYViewHierarchyActivity");
        intent.setType("data/view");

        intent.putExtra(Intent.EXTRA_TEXT, msg);
        act.startActivity(intent);
    }

    public static void printClassMethods2ExportedActivity(Activity act){
        Method[] methods = act.getClass().getDeclaredMethods();
        String str = "";
        for(Method f:methods){
            str = str + f.getName() + ";";
        }
        // after serializing, we start it
        printMsg2ExportedActivity(act, str);
    }

    public static void printFields2ExportedActivity(Activity act){
        Field[] fields = act.getClass().getFields();
        String str = "";
        for(Field f:fields){
            str = str + f.getName() + ";";
        }
        printMsg2ExportedActivity(act, str);
    }

    public static void printViewTree2ExportedActivity(Activity act){

        View v = act.findViewById(android.R.id.content);
        ViewGroup rootView = (ViewGroup)v;
        // since it only has one child;
        // rootView = (ViewGroup) rootView.getChildAt(0);

        // display in jd's page(activity etc.)
        Logger.toast_i_long(act, rootView.toString());

        // not working
        Utils.printViewHierarchy(rootView, "WY_");
        // Logger.i(rootView.getId() + "");
        Object result = recursiveLoopChildren(rootView);
        String str = result.toString();

        printMsg2ExportedActivity(act, str);
    }



    public static void sendBroadcast2ExportedActivity(Activity act, String msg){
        Intent intent =new Intent();
        intent.setAction("com.wy.listen_log");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        act.sendBroadcast(intent);
    }


    // https://stackoverflow.com/questions/2597230/loop-through-all-subviews-of-an-android-view

    public static Object recursiveLoopChildren(ViewGroup parent) {

        // for each viewGroup, we must have one node;
        Object node = null;
        if(parent.getChildCount() > 1){
            node = new JSONArray();
        }else{
            node = new JSONObject();
        }

        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                Object innerNode = recursiveLoopChildren((ViewGroup) child);
                putIn(node, null,innerNode);
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
            } else {
                if (child != null) {
                    // DO SOMETHING WITH VIEW
                    putIn(node, child, null);
                }
            }
        }
        return node;
    }

    private static void putIn(Object node, View v, Object inner){
        if(null != v){
            if(node instanceof JSONObject){
                putInJson((JSONObject) node, v);
            }
            if(node instanceof JSONArray){
                putInArray((JSONArray) node, v);
            }
        }else{
            if(node instanceof JSONObject){
                try {
                    ((JSONObject)node).put("group", inner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(node instanceof JSONArray){
                ((JSONArray)node).put(inner);
            }
        }

    }

    private static void putInJson(JSONObject json, View v){
        try {
            json.put("child", v.toString());
        } catch (JSONException e) {
            try {
                json.put("child", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }
    private static void putInArray(JSONArray array, View v){
        array.put(v.toString());
    }

}
