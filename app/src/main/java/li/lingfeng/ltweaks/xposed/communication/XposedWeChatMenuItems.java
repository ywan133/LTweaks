package li.lingfeng.ltweaks.xposed.communication;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.xposed.XposedBase;
import li.lingfeng.ltweaks.ywhook.YWUtilsForWechat;
import li.lingfeng.ltweaks.ywhook.YWUtilsLogger;

/**
 * Created by yangwan on 18/06/2017.
 */
@XposedLoad(packages = PackageNames.WE_CHAT, prefs = R.string.key_wechat_wy_menu_items)
public class XposedWeChatMenuItems extends XposedBase {

    private final static String actTmp15 = "com.tencent.mm.ui.LauncherUI";
    private Activity activity;
    private MenuItem showViewTreeBtn;
    private MenuItem showFindViewTest;
    private MenuItem showActivityMethods;

    @Override
    protected void handleLoadPackage() throws Throwable {
        findAndHookActivity(actTmp15, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                // Logger.toast_i(activity, "onResume, LauncherUI");
            }



        });
        findAndHookActivity(actTmp15, "onCreateOptionsMenu", Menu.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                // Logger.toast_i(activity, "onCreateOptionsMenu, LauncherUI");
                Menu menu = (Menu) param.args[0];

                showViewTreeBtn = menu.add(Menu.NONE, 12345, 0, "显示View树");
                showFindViewTest = menu.add(Menu.NONE, 123456, 0, "查找view-text");
                showActivityMethods = menu.add(Menu.NONE, 123457, 0, "显示methods");
                showViewTreeBtn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            }
        });
        findAndHookActivity(actTmp15, "onOptionsItemSelected", MenuItem.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                MenuItem item = (MenuItem) param.args[0];
                if (item == showViewTreeBtn) {
                    YWUtilsLogger.printViewTree2ExportedActivity(activity, null);
                }
                if (item == showFindViewTest) {
                    // find "聪明的我们"
                    ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
                    View v = YWUtilsForWechat.recursiveLoopChildrenFind(rootView, "聪明的我们");
                    if(null != v) {
                        Logger.toast_i_long(activity, v.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found");
                    }
                }
                if(item == showActivityMethods){
                    YWUtilsLogger.printClassMethods2ExportedActivity(activity, null);
                }


            }
        });

        findAndHookActivity(actTmp15, "onStop", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                // Logger.toast_i(activity, "onStop, LauncherUI");
            }
        });
        findAndHookActivity(actTmp15, "onDestroy", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                activity = (Activity) param.thisObject;
                // Logger.toast_i(activity, "onDestroy, LauncherUI");
            }
        });


    }
}
