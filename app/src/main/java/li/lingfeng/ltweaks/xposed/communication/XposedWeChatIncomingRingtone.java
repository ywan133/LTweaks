package li.lingfeng.ltweaks.xposed.communication;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.prefs.Prefs;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.xposed.XposedBase;
import li.lingfeng.ltweaks.ywhook.YWUtilsForWechat;
import li.lingfeng.ltweaks.ywhook.YWUtilsLogger;

/**
 * Created by smallville on 2017/1/21.
 */
@XposedLoad(packages = PackageNames.WE_CHAT, prefs = R.string.key_wechat_use_incoming_ringtone)
public class XposedWeChatIncomingRingtone extends XposedBase {

    private Activity activity;
    private MenuItem showViewTreeBtn;
    private MenuItem showFindViewTest;
    private MenuItem showActivityMethods;

    @Override
    public void handleLoadPackage() throws Throwable {
        findAndHookMethod(MediaPlayer.class, "setDataSource", Context.class, Uri.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                Uri uri = (Uri) param.args[1];
                Logger.i("Setting media source, original is " + uri.toString());

                int idPhonering =context.getResources().getIdentifier("phonering", "raw", "com.tencent.mm");
                if (uri.toString().equals("android.resource://com.tencent.mm/" + idPhonering)) {
                    String path = Prefs.instance().getString(R.string.key_wechat_set_incoming_ringtone, "");
                    param.args[1] = Uri.parse(path);
                    Logger.i("Media source is changed to " + path);
                }
            }
        });

        String actTmp0 = "com.tencent.mm.plugin.sns.lucky.ui.SnsLuckyMoneyReceiveDetailUI";
        String actTmp1 = "com.tencent.mm.plugin.sns.lucky.ui.SnsLuckyMoneyUnReceiveDetailUI";
        String actTmp2 = "com.tencent.mm.plugin.sns.lucky.ui.SnsLuckyMoneyDetailUI";


        findAndHookActivity(actTmp0, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, SnsLuckyMoneyReceiveDetailUI");
            }
        });
        findAndHookActivity(actTmp1, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, SnsLuckyMoneyUnReceiveDetailUI");
            }
        });
        findAndHookActivity(actTmp2, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, SnsLuckyMoneyDetailUI");
            }
        });

        // 这是自己的朋友圈的,  查看消息的 activity
        String actTmp3 = "com.tencent.mm.plugin.sns.ui.SnsMsgUI";
        // 这是某人的朋友圈(查看相册)的   页面 activity
        String actTmp4 = "com.tencent.mm.plugin.sns.ui.SnsUserUI";
        String actTmp5 = "com.tencent.mm.plugin.sns.ui.SnsGalleryUI";

        String actTmp6 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyIndexUI";
        String actTmp7 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyPrepareUI";
        String actTmp8 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyNewYearReceiveUI";
        String actTmp9 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyBusiReceiveUI";
        String actTmp10 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyMyRecordUI";
        String actTmp11 = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";

        String actTmp12 = "com.tencent.mm.plugin.talkroom.ui.TalkRoomUI";

        findAndHookActivity(actTmp3, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, SnsMsgUI");
            }
        });
        findAndHookActivity(actTmp4, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, SnsUserUI");
            }
        });

        findAndHookActivity(actTmp6, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, LuckyMoneyIndexUI");
            }
        });

        findAndHookActivity(actTmp12, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, TalkRoomUI");
            }
        });

        String actTmp13 = "com.tencent.mm.ui.chatting.En_5b8fbb1e";
        String actTmp14 = "com.tencent.mm.ui.chatting.AtSomeoneUI";
        findAndHookActivity(actTmp13, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, En_5b8fbb1e");
            }
        });
        findAndHookActivity(actTmp14, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                Logger.toast_i(activity, "onResume, AtSomeoneUI");
            }
        });


        String actTmp15 = "com.tencent.mm.ui.LauncherUI";
        findAndHookActivity(actTmp15, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                activity = (Activity) param.thisObject;
                // Logger.toast_i(activity, "onResume, LauncherUI");
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
        String actTmp16_view = "com.tencent.mm.ui.LauncherUIBottomTabView";
        hookAllMethods(actTmp16_view, "init", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Logger.i("init, LauncherUIBottomTabView");
                if(null != activity) {
                    Logger.toast_i(activity, "init, LauncherUIBottomTabView");
                }
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
    }
}
