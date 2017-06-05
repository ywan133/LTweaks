package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import java.net.URLEncoder;
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

                    Logger.toast_i_long(mActivity, rootView.toString());
                    Utils.printViewHierarchy(rootView, "WY_");
                    Logger.i(rootView.getId() + "");

                    // Intent intent = new Intent(mActivity, WYViewHierarchyActivity.class);
                    Intent intent = new Intent();
                    intent.setClassName(
                            // Your app's package name
                            "li.lingfeng.ltweaks",
                            // The full class name of the activity you want to start
                            "li.lingfeng.ltweaks.activities.WYViewHierarchyActivity");
                    intent.setType("data/view");
                    intent.putExtra(Intent.EXTRA_TEXT, rootView.toString());
                    mActivity.startActivity(intent);


                }catch (Exception e){
                    Logger.e(e.getMessage());
                    Logger.e("MainActivity onResume");
                }
            }

        });
    }

}
