package li.lingfeng.ltweaks.xposed.communication;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;

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
    private final static String HOME_UI = "com.tencent.mm.ui.HomeUI";
    private Activity activity;
    private MenuItem showViewTreeBtn;
    private MenuItem showFindViewTest;
    private MenuItem showFindViewLayout;
    private MenuItem showActivityMethods;
    private MenuItem showActivityFields;

    private MenuItem showFindHomeUI;
    private MenuItem showFindHomeUIFields;
    private MenuItem showFindHomeUIFieldsLinkedList;
    private MenuItem showFindHomeUIFieldsHashMap;

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

                showViewTreeBtn = menu.add(Menu.NONE, 1000, 0, "显示View树");
                showFindViewTest = menu.add(Menu.NONE, 1001, 0, "查找 聪明的我们");
                showFindViewLayout = menu.add(Menu.NONE, 1002, 0, "查找 layout");
                showActivityMethods = menu.add(Menu.NONE, 1003, 0, "显示methods");
                showActivityFields = menu.add(Menu.NONE, 1004, 0, "显示fields");

                showFindHomeUI = menu.add(Menu.NONE, 1005, 0, "HomeUI methods");
                showFindHomeUIFields = menu.add(Menu.NONE, 1006, 0, "HomeUI fields");
                showFindHomeUIFieldsLinkedList = menu.add(Menu.NONE, 1007, 0, "HomeUI fields (LinkedList)");
                showFindHomeUIFieldsHashMap = menu.add(Menu.NONE, 1008, 0, "HomeUI fields (HashMap)");

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
                if (item == showFindViewLayout) {
                    // 方法1, 失败,没找到
                    // find "聪明的我们"
                    // ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
                    // 方法2, 失败, 没找到
                    // View v = YWUtilsForWechat.recursiveLoopChildrenFindType(rootView, "com.tencent.mm.ui.widget.SwipeBackLayout");
                    // 方法3, 失败, 竟然为空
                    Object v = YWUtilsForWechat.findFieldRef(activity, YWUtilsForWechat.SwipeBackLayout);
                    // 方法4, ...
                    if(null != v) {
                        Logger.toast_i_long(activity, v.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found layout");
                    }
                }


                if(item == showActivityMethods){
                    YWUtilsLogger.printClassMethods2ExportedActivity(activity, null);
                }
                if(item == showActivityFields){
                    YWUtilsLogger.printFields2ExportedActivity(activity, null);
                }
                if(item == showFindHomeUI){
                    Object v = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    if(null != v) {
                        YWUtilsLogger.printClassMethods2ExportedActivity(activity, v);
                        Logger.toast_i_long(activity, v.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found HOME_UI class methods");
                    }
                }
                if(item == showFindHomeUIFields){
                    Object v = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    if(null != v) {
                        YWUtilsLogger.printFields2ExportedActivity(activity, v);
                        Logger.toast_i_long(activity, v.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found HOME_UI fields");
                    }
                }
                if(item == showFindHomeUIFieldsLinkedList){
                    Object homeUI = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    LinkedList tQp = (LinkedList) YWUtilsForWechat.findFieldRef(homeUI, "java.util.LinkedList");
                    if(null != tQp) {
                        YWUtilsLogger.printLinedList(activity, tQp);
                        Logger.toast_i_long(activity, tQp.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found HOME_UI fields linkedList");
                    }
                }
                if(item == showFindHomeUIFieldsHashMap){
                    Object homeUI = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    HashMap tQr = (HashMap) YWUtilsForWechat.findFieldRef(homeUI, "java.util.HashMap");
                    if(null != tQr) {
                        YWUtilsLogger.printMap(activity, tQr);
                        Logger.toast_i_long(activity, tQr.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found HOME_UI fields hashMap");
                    }
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

                // activity = (Activity) param.thisObject;
                // Logger.toast_i(activity, "onDestroy, LauncherUI");
                activity = null;
                showViewTreeBtn = null;
                showFindViewTest = null;
                showActivityMethods = null;
                showActivityFields = null;
            }
        });


    }
}
