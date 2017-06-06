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

                    // Utils.printClassMethods2ExportedActivity(mActivity);
                    // Utils.printFields2ExportedActivity(mActivity);
                    Utils.printViewTree2ExportedActivity(mActivity);
                    Utils.sendBroadcast2ExportedActivity(mActivity, "onResume");

                }catch (Exception e){
                    Logger.e(e.getMessage());
                    Logger.e("MainActivity onResume");
                }
            }


        });
        findAndHookActivity(MAIN_ACTIVITY, "onDestroy", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mActivity = (Activity) param.thisObject;

                Utils.sendBroadcast2ExportedActivity(mActivity, "onDestroy");


            }
        });
    }

}
