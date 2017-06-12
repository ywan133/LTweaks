package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.InputDeviceCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.utils.Utils;
import li.lingfeng.ltweaks.utils.WYHookie;
import li.lingfeng.ltweaks.utils.YWUtilsForMainFrameActivity;
import li.lingfeng.ltweaks.utils.YWUtilsLogger;
import li.lingfeng.ltweaks.xposed.XposedBase;

/**
 * Created by yangwan on 04/06/2017.
 */
@XposedLoad(packages = PackageNames.JD, prefs = R.string.key_jd_sign_in_auto)
public class XposedAutoSignInClip extends XposedBase {

    // 这个, 貌似是开始的那个activity
    // 里面有广告...
    private static final String MAIN_ACTIVITY = "com.jingdong.app.mall.main.MainActivity";
    // 这个貌似是真的...
    private static final String MAIN_FRAME_ACTIVITY = "com.jingdong.app.mall.MainFrameActivity";

    // com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.JDGridViewInViewPager
    private static final String JD_GRID_VIEW_PAGER = "com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.JDGridViewInViewPager";
    private static final String JD_GRID_VIEW_PAGER_GRID_VIEW = "com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.JDViewPagerWithGridView";
    private static final String JD_GRID_VIEW_PAGER_ICON = "com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon";
    private static final String JD_GRID_VIEW_JDCRIDVIEW_BASE = "com.jingdong.common.ui.JDGridView";


    // com/jingdong/app/mall/home/floor/view/view/MallFloor_Icon$IconViewPagerAdapter
    private static final String JD_ICON_VIEW_PAGER_ADAPTER = "com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.IconViewPagerAdapter";

    private static final String JD_MallFloor_Icon = "com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon";

    private Activity mActivity;
    private Object mallFloor_Icon;

    @Override
    protected void handleLoadPackage() throws Throwable {

        successHook_MainFrameActivity();
        testHook_JD_MallFloor_Icon();
        // testHook_JDGridViewInViewPager();
        // testHook_JD_GRID_VIEW_PAGER_GRID_VIEW();

        /*
        // onInterceptTouchEvent
        // onTouchEvent
        findAndHookMethod(JD_GRID_VIEW_PAGER, "onInterceptTouchEvent", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                Object current = param.thisObject;
                Logger.toast_i(mActivity, current.toString());
                Logger.toast_i(mActivity, Arrays.toString(param.args));

            }
        });
        */





        /*
        // Can't handleLoadPackage, java.lang.ClassNotFoundException: Lcom.jingdong.app.mall.home.floor.view.view.MallFloor_Icon
        findAndHookConstructor(JD_MallFloor_Icon, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mallFloor_Icon = param.thisObject;
                Logger.toast_i_long(mActivity, mallFloor_Icon.toString());
            }
        });
        */



    }

    WYHookie hookie = null;
    private void successHook_MainFrameActivity(){
        findAndHookActivity(MAIN_FRAME_ACTIVITY, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                mActivity = (Activity) param.thisObject;
                // YWUtilsForMainFrameActivity.invokeIt(mActivity, mallFloor_Icon);
                if(null == hookie){
                    hookie = new WYHookie(param);
                }

                hookie.hookOnResume();
            }



            private void printViewItSelf(Activity act, View child){

                // Utils.printFields2ExportedActivity(act, child);

                String msg = child.getClass().getCanonicalName() + "|";
                msg += child.getClass().getName() + "|";
                msg += child.getClass().getSimpleName() + "|";
                // Utils.printMsg2ExportedActivity(mActivity, msg);
                // Utils.printClassMethods2ExportedActivity(mActivity, child);
                // Utils.printViewTree2ExportedActivity(act, (ViewGroup) child);
                child.setBackgroundColor(Color.RED);

                // result: yes
                // boolean isClickable = child.isClickable();
                // Logger.toast_i_long(act, "pager: clickable: " + isClickable);

                child.performClick();
                child.callOnClick();
            }

            private void failed2CallTouchEvent(View child){

                long temT = SystemClock.uptimeMillis();
                long temT2 = SystemClock.uptimeMillis() + 100;
                float x = 0.0f;
                float y = 0.0f;
                MotionEvent me = MotionEvent.obtain(temT,temT2, MotionEvent.ACTION_DOWN, x, y, 0);



                long when = SystemClock.uptimeMillis();
                int source = InputDeviceCompat.SOURCE_TOUCHSCREEN;
                float pressure = 1.0f;
                int action = 0;
                MotionEvent event = MotionEvent.obtain(when, when, action, x, y, pressure, 1.0f, 0, 1.0f, 1.0f, 0, 0);
                event.setSource(source);
                me = event;


                child.dispatchTouchEvent(me);
                child.onTouchEvent(me);
                ((ViewGroup) child).onInterceptTouchEvent(me);

                mActivity.dispatchTouchEvent(me);
                mActivity.onTouchEvent(me);




                Logger.toast_i(mActivity, me.toString());
            }



        });
        findAndHookActivity(MAIN_ACTIVITY, "onDestroy", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mActivity = null;
            }
        });
    }

    // 成功了, 但并无用处, 可以拿到它的坐标, 还没有尝试是否能成功
    private void testHook_JD_GRID_VIEW_PAGER_GRID_VIEW(){
        hookAllMethods(JD_GRID_VIEW_PAGER_GRID_VIEW, "onTouchEvent", new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object current = param.thisObject;

                String msg = current.toString() + Arrays.toString(param.args);
                // Logger.toast_i_long(mActivity, msg);
                YWUtilsLogger.printMsg2ExportedActivity(mActivity, msg);
            }
        });
    }

    // not work...
    // please try again
    private void testHook_JDGridViewInViewPager(){
        hookAllMethods(JD_GRID_VIEW_PAGER, "setAdapter", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object current = param.thisObject;

                Logger.toast_i_long(mActivity, current.toString() + Arrays.toString(param.args));
            }
        });
    }

    // createGridView成功了,
    // constructors也是成功的,
    private void testHook_JD_MallFloor_Icon(){
        // JD_ICON_VIEW_PAGER_ADAPTER, "addView", android.view.View.class
        hookAllConstructors(JD_MallFloor_Icon,  new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Object current = param.thisObject;
                if(null == mActivity){
                    mActivity = (Activity) param.args[0];
                }

                //
                // Logger.toast_i_long(mActivity, current.toString());
                // Logger.toast_i(mActivity, Arrays.toString(param.args));
                // Utils.printFields2ExportedActivity(mActivity, current);
                // Utils.printClassMethods2ExportedActivity(mActivity, current);
                // Logger.toast_i(mActivity, Arrays.toString(param.args));

            }
        });
        //[com.jingdong.app.mall.home.floor.view.adapter.d@72fae4d, 5, 27, android.widget.RelativeLayout$LayoutParams@c613e9b, 10]
        // .method private createGridView(Lcom/jingdong/app/mall/home/floor/view/adapter/d;IILandroid/widget/RelativeLayout$LayoutParams;I)Lcom/jingdong/app/mall/home/floor/view/view/MallFloor_Icon$JDGridViewInViewPager;
        hookAllMethods(JD_MallFloor_Icon, "createGridView", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                mallFloor_Icon = param.thisObject;


                // Logger.toast_i(mActivity, "createGridView...");
                // Logger.toast_i(mActivity, Arrays.toString(param.args));
                Object adapter = param.args[0];
                Object _hooked_int = param.args[2];
                Object result = param.getResultOrThrowable(); // viewPager
                // Utils.printMsg2ExportedActivity(mActivity, adapter.getClass().getCanonicalName());
                // Object result = Utils.invokeMeth(adapter, "getItem", 1);// null
                // Utils.printMsg2ExportedActivity(mActivity, adapter.getClass().getCanonicalName());
                //Utils.printClassMethods2ExportedActivity(mActivity, result);
            }
        });

        // onAllIconLoaded
        // onLayout
        hookAllMethods(JD_MallFloor_Icon, "onLayout", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                mallFloor_Icon = param.thisObject;
                // Logger.toast_i(mActivity, "onLayout");

                // YWUtilsForMainFrameActivity.invokeIt(mActivity, mallFloor_Icon);
                hookie.hookOnLayout();

            }
        });
        hookAllMethods(JD_MallFloor_Icon, "onIconItemClick", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Logger.toast_i_long(mActivity, String.valueOf(param.args[0]) + "|" + param.args[1]);


            }
        });
        hookAllMethods(JD_MallFloor_Icon, "access$000", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Logger.toast_i(mActivity, "access$000");
            }
        });
    }

}
