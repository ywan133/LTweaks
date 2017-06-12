package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by yangwan on 08/06/2017.
 */

public class YWUtilsLogger {

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
    public static void printMsg2ExportedActivity(Activity act, Throwable t){
        YWUtilsLogger.printMsg2ExportedActivity(act, Arrays.toString(t.getStackTrace()));
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

    public static void printClassMethods2ExportedActivity(Activity act, Object target){
        if(null == target){
            target = act;
        }
        Method[] methods = target.getClass().getDeclaredMethods();
        String str = "";
        for(Method f:methods){
            str = str + f.getName() + ";";
        }
        // after serializing, we start it
        printMsg2ExportedActivity(act, str);
    }

    public static void printFields2ExportedActivity(Activity act, Object target){
        if(null == target){
            target = act;
        }
        Field[] fields = target.getClass().getFields();
        String str = "";
        for(Field f:fields){
            str = str + f.getName() + ";";
        }
        printMsg2ExportedActivity(act, str);
    }



    public static void printViewTree2ExportedActivity(Activity act, ViewGroup view){

        if(null == view){
            view = (ViewGroup) act.findViewById(android.R.id.content);
        }

        // since it only has one child;
        // rootView = (ViewGroup) rootView.getChildAt(0);

        // display in jd's page(activity etc.)
        Logger.toast_i_long(act, view.toString());

        // not working
        printViewHierarchy(view, "WY_");
        // Logger.i(rootView.getId() + "");
        Object result = recursiveLoopChildren(view);
        String str = result.toString();

        // printMsg2ExportedActivity(act, str);
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
                    // 全部的名字:
                    // String name = child.getClass().getCanonicalName();
                    // 简单的名字
                    String name = child.getClass().getSimpleName();

                    if(child instanceof TextView){
                        name += ((TextView)child).getText();
                    }

                    putIn(node, name, null);
                }
            }
        }
        return node;
    }
    private static void putIn(Object node, String v, Object inner){
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
                    String groupName = inner.getClass().getCanonicalName();
                    ((JSONObject)node).put(groupName, inner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(node instanceof JSONArray){
                ((JSONArray)node).put(inner);
            }
        }

    }

    private static void putInJson(JSONObject json, String v){
        try {
            json.put("child", v);
        } catch (JSONException e) {
            try {
                json.put("child", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }
    private static void putInArray(JSONArray array, String v){
        array.put(v.toString());
    }



}
