package li.lingfeng.ltweaks.ywhook;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import li.lingfeng.ltweaks.utils.Logger;

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
        Field[] fields = target.getClass().getDeclaredFields();
        String str = "";
        for(Field f:fields){
            // com.tencent.mm.ui.HomeUI:tRc;
            // boolean:tRf;
            // long:tRb;
            // int:RESULT_OK;
            str = str + f.getType().getCanonicalName() + ":" + f.getName() + ";\n\n\n";

            if("java.util.ArrayList".equals(f.getType().getCanonicalName())){
                try {
                    f.setAccessible(true);
                    ArrayList arr = (ArrayList) f.get(target);
                    for (Object obj: arr){
                        str = str + "\t\t\t" + obj.getClass().getCanonicalName() + ":" + obj.toString() + ";\n";
                    }
                    // finally:
                    str = str + "\n";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    str = str + e.getMessage() + "\n";
                }
            }
            if("java.util.LinkedList".equals(f.getType().getCanonicalName())){
                try {
                    f.setAccessible(true);
                    LinkedList arr = (LinkedList) f.get(target);
                    for (Object obj: arr){
                        str = str + "\t\t\t" + obj.getClass().getCanonicalName() + ":" + obj.toString() + ";\n";
                    }
                    // finally:
                    str = str + "\n";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    str = str + e.getMessage() + "\n";
                }
            }
            if("java.util.HashMap".equals(f.getType().getCanonicalName())){
                try {
                    f.setAccessible(true);
                    HashMap arr = (HashMap) f.get(target);
                    for (Object obj: arr.entrySet()){
                        str = str + "\t\t\t" + obj.getClass().getCanonicalName() + ":" + obj.toString() + ";\n";
                    }
                    // finally:
                    str = str + "\n";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    str = str + e.getMessage() + "\n";
                }
            }


        }
        printMsg2ExportedActivity(act, str);
    }

    public static void printLinedList(Activity activity, LinkedList tQp){
        String str = "";
        for (Object obj: tQp){
            str = str + obj.getClass().getCanonicalName() + ":" + obj.toString() + ";\n\n";
        }
        Logger.i("Length of linkedList:" + tQp.size());
        Logger.i("str of linkedList:" + str);
        printMsg2ExportedActivity(activity, str);
    }
    public static void printMap(Activity activity, HashMap tQr){
        String str = "";
        for (Object entry: tQr.entrySet()){
            str = str + entry.getClass().getCanonicalName() + ":" + entry.toString() + ";\n\n";
        }
        printMsg2ExportedActivity(activity, str);
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

        printMsg2ExportedActivity(act, str);
    }



    public static void sendBroadcast2ExportedActivity(Activity act, String msg){
        Intent intent =new Intent();
        intent.setAction("com.wy.listen_log");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        act.sendBroadcast(intent);
    }



    // https://stackoverflow.com/questions/2597230/loop-through-all-subviews-of-an-android-view
    public static JSONObject recursiveLoopChildren(ViewGroup parent) {

        // for each viewGroup, we must have one node;
        // 无论是 viewGroup 还是view, 我们永远有一对key-value json object
        JSONObject node = new JSONObject();
        JSONArray inner = null;
        boolean parentIsAViewGroup = false;

        // 如果是viewGroup, 我们特殊处理一下:
        if(parent.getChildCount() > 1){
            parentIsAViewGroup = true;
            inner = new JSONArray();
            try {
                node.put(parent.getClass().getSimpleName(), inner);
            } catch (JSONException e) {
                try {
                    node.put(parent.getClass().getSimpleName(), e.getMessage());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }

        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                JSONObject innerNode =  recursiveLoopChildren((ViewGroup) child);
                // putIn(node, null, innerNode);
                // putInJson(node, innerNode);

                if(parentIsAViewGroup){
                    inner.put(innerNode);
                }else{
                    try {
                        node.put(child.getClass().getSimpleName(), innerNode);
                    } catch (JSONException e) {
                        try {
                            node.put(child.getClass().getSimpleName(), e.getMessage());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
            } else {
                if (child != null) {
                    // DO SOMETHING WITH VIEW
                    // 全部的名字:
                    // String name = child.getClass().getCanonicalName();
                    // 简单的名字
                    String name = child.getClass().getSimpleName();
                    Object title = YWUtilsForMainFrameActivity.invokeNoParamMeth(child, "getText");
                    if(null != title && title instanceof String && ((String) title).length() > 0)
                        name += "(" +title + ")";

                    if(parentIsAViewGroup){
                        // 它的子孙
                        putInArray(inner, name);
                    }else{
                        Logger.e("parentIsAViewGroup is false!?");
                        putInJson(node, name);
                    }
                }
            }
        }
        return node;
    }

    /**
     *
     * @param node      JsonObject or JsonArray
     * @param v         JsonObject的话, 它是其class name + text内容
     * @param inner     应该是个JsonArray?
     */
    private static void putIn(Object node, String v, Object inner){
        if(null != v){
            if(node instanceof JSONObject){
                putInJson((JSONObject) node, v);
            }
            if(node instanceof JSONArray){
                putInArray((JSONArray) node, v);
            }
        }else{
            // 这里存的是
            if(node instanceof JSONObject){
                try {
                    String groupName = inner.getClass().getSimpleName();
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
