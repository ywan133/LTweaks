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
    private final static String CONVERSATION_VIEW = "com.tencent.mm.ui.conversation.ConversationOverscrollListView";

    private Activity activity;
    private MenuItem showViewTreeBtn;
    private MenuItem showFindViewTest;

    private MenuItem showFindViewLayout;
    private MenuItem showFindViewPager;
    private MenuItem showFindViewPagerFields;

    private MenuItem showActivityMethods;
    private MenuItem showActivityFields;

    private MenuItem showFindHomeUI;
    private MenuItem showFindHomeUIFields;

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

                showViewTreeBtn = menu.add(Menu.NONE, 1000, 0, "LauncherUI View树");
                showFindViewTest = menu.add(Menu.NONE, 1001, 0, "LauncherUI 查找 聪明的我们");
                showFindViewLayout = menu.add(Menu.NONE, 1002, 0, "LauncherUI 查找 layout");

                showActivityMethods = menu.add(Menu.NONE, 1003, 0, "LauncherUI 显示 methods");
                showActivityFields = menu.add(Menu.NONE, 1004, 0, "LauncherUI 显示 fields");

                showFindHomeUI = menu.add(Menu.NONE, 1005, 0, "HomeUI methods");
                showFindHomeUIFields = menu.add(Menu.NONE, 1006, 0, "HomeUI fields");

                showFindViewPager = menu.add(Menu.NONE, 1007, 0, "showFindViewPager methods");
                showFindViewPagerFields  = menu.add(Menu.NONE, 1008, 0, "showFindViewPager fields");

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
                if(item == showFindViewPager){
                    Object homeUI = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    Object viewPager = YWUtilsForWechat.findFieldRef(homeUI, YWUtilsForWechat.CustomViewPager);
                    if(null != viewPager) {
                        YWUtilsLogger.printClassMethods2ExportedActivity(activity, viewPager);
                        Logger.toast_i_long(activity, viewPager.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found viewPager (methods)");
                    }
                }
                if(item == showFindViewPagerFields){
                    Object homeUI = YWUtilsForWechat.findFieldRef(activity, HOME_UI);
                    Object viewPager = YWUtilsForWechat.findFieldRef(homeUI, YWUtilsForWechat.CustomViewPager);
                    if(null != viewPager) {
                        YWUtilsLogger.printFields2ExportedActivity(activity, viewPager);
                        Logger.toast_i_long(activity, viewPager.toString());
                    }else{
                        Logger.toast_i_long(activity, "not found viewPager (fields)");
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

        hookAllConstructors(CONVERSATION_VIEW, new XC_MethodHook(){
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try{
                    Object conView = param.thisObject;
                    Object what = param.args[0];
                    Logger.toast_i(activity, what.toString());
                }catch (Throwable t){
                    Logger.stackTrace(t);
                }
            }
        });
        hookAllMethods(CONVERSATION_VIEW, "onItemClick", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Object what = param.args[1];
                YWUtilsLogger.printFields2ExportedActivity(activity, what);
            }
        });

    }
}
