package li.lingfeng.ltweaks.ywhook;

import android.app.Activity;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by yangwan on 12/06/2017.
 */

public class WYHookieMallFloor_Icon {

    // target
    private Object mallFloor_Icon;
    private Activity activity;

    private static WYHookieMallFloor_Icon hookie = null;

    public static WYHookieMallFloor_Icon getInstance(XC_MethodHook.MethodHookParam param, Activity activity){
        if(null == hookie){
            synchronized (WYHookieMallFloor_Icon.class){
                if(null == hookie){
                    hookie = new WYHookieMallFloor_Icon(param, activity);
                }
            }
        }
        return hookie;
    }
    // any hooked method should do
    private WYHookieMallFloor_Icon(XC_MethodHook.MethodHookParam param, Activity activity) {
        mallFloor_Icon = param.thisObject;

        this.activity = activity;
    }

    public void hookCreateGridView(XC_MethodHook.MethodHookParam param){
        try {
            Object adapter = param.args[0];
            Object _hooked_int = param.args[2];
            Object result = param.getResultOrThrowable(); // viewPager
            // Utils.printMsg2ExportedActivity(mActivity, adapter.getClass().getCanonicalName());
            // Object result = Utils.invokeMeth(adapter, "getItem", 1);// null
            // Utils.printMsg2ExportedActivity(mActivity, adapter.getClass().getCanonicalName());
            //Utils.printClassMethods2ExportedActivity(mActivity, result);
        }catch (Throwable t){
            YWUtilsLogger.printMsg2ExportedActivity(activity, t);
        }
    }
}
