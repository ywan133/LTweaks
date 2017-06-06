package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.activities.WYViewHierarchyActivity;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.utils.Utils;
import li.lingfeng.ltweaks.xposed.XposedBase;

/**
 * Created by yangwan on 04/06/2017.
 */
@XposedLoad(packages = PackageNames.JD, prefs = R.string.key_jd_sign_in_auto)
public class XposedAutoSignInClip extends XposedBase {

    private static final String MAIN_ACTIVITY = "com.jingdong.app.mall.main.MainActivity";
    private Activity mActivity;


    @Override
    protected void handleLoadPackage() throws Throwable {

        findAndHookActivity(MAIN_ACTIVITY, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                mActivity = (Activity) param.thisObject;
                try{
                    String m = "MainActivity onResume kkkkkkk";
                    XposedBridge.log(m);
                    Logger.e(m);
                    Logger.v(m);
                    Logger.i(m);
                    Logger.w(m);



                    View v = mActivity.findViewById(android.R.id.content);
                    ViewGroup rootView = (ViewGroup)v;
                    // since it only has one child;
                    rootView = (ViewGroup) rootView.getChildAt(0);

                    Logger.toast_i_long(mActivity, rootView.toString());

                    // Utils.printViewHierarchy(rootView, "WY_");
                    // Logger.i(rootView.getId() + "");

//                    Object result = recursiveLoopChildren(rootView);
//                    String str = result.toString();



                    // Field[] fields = mActivity.getClass().getFields();
//                    String str = "";
//                    for(Field f:fields){
//                        str = str + f.getName() + ";";
//                    }

                    Method[] methods = mActivity.getClass().getDeclaredMethods();
                    String str = "";
                    for(Method f:methods){
                        str = str + f.getName() + ";";
                    }

                    Utils.printMsg2ExportedActivity(mActivity, str);

                }catch (Exception e){
                    Logger.e(e.getMessage());
                    Logger.e("MainActivity onResume");
                }
            }
            // https://stackoverflow.com/questions/2597230/loop-through-all-subviews-of-an-android-view

            public Object recursiveLoopChildren(ViewGroup parent) {

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

            private void putIn(Object node, View v, Object inner){
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

            private void putInJson(JSONObject json, View v){
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
            private void putInArray(JSONArray array, View v){
                array.put(v.toString());
            }

        });
    }

}
