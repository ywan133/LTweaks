package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.InputDeviceCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.utils.Utils;
import li.lingfeng.ltweaks.xposed.XposedBase;
import okhttp3.internal.Util;

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

        successHook();
        testHook_JD_MallFloor_Icon();
        // testHook_JDGridViewInViewPager();
        testHook_JD_GRID_VIEW_PAGER_GRID_VIEW();

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

    private void successHook(){
        findAndHookActivity(MAIN_FRAME_ACTIVITY, "onResume", new XC_MethodHook() {
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

                    // Utils.printClassMethods2ExportedActivity(mActivity, null);
                    // Utils.printFields2ExportedActivity(mActivity, null);
                    // Utils.printViewTree2ExportedActivity(mActivity, null);
                    // Utils.sendBroadcast2ExportedActivity(mActivity, "onResume");
                    View v = mActivity.findViewById(android.R.id.content);
                    recursiveLoopChildren((ViewGroup) v);

                }catch (Exception e){
                    Logger.e(e.getMessage());
                    Logger.e("MainActivity onResume");
                }
            }

            private View viewPager = null;
            private View viewPagerWithGridView = null;
            private List<View> viewPagerWithGridViews = new ArrayList<View>();
            public void recursiveLoopChildren(ViewGroup parent) {
                for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                    final View child = parent.getChildAt(i);
                    if (child instanceof ViewGroup) {
                        recursiveLoopChildren((ViewGroup) child);
                        // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED

                        // Class _class = Class.forName("JDGridViewInViewPager");
                        // JDGridViewInViewPager
                        if( child.getClass().getSimpleName().equals("JDGridViewInViewPager") ){
                            // Logger.toast_i_long(mActivity, "Found JDGridViewInViewPager");

                            // com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.JDGridViewInViewPager;
                            // Logger.toast_i_long(mActivity, child.getClass().getCanonicalName());
                            // Utils.sendBroadcast2ExportedActivity(mActivity, "onResume");

                            viewPager = child;
                            // printViewItSelf(mActivity, child);
                        }

                    } else {
                        if (child != null) {
                            if( child.getClass().getSimpleName().equals("JDViewPagerWithGridView") ){
                                viewPagerWithGridViews.add(child);
                            }
                            // DO SOMETHING WITH VIEW
                            find京豆Widget(child, parent);
                        }
                    }
                }
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

            private void find京豆Widget(View child, ViewGroup parent){
                java.lang.reflect.Method method;
                try {
                    method = child.getClass().getMethod("getText");
                    if(null != method){
                        Object title = method.invoke(child);
                        if(title instanceof String && title.equals("领京豆")){
                            // Logger.toast_i_long(mActivity, "Found it: 领京豆");
                            // Logger.toast_i_long(mActivity, child.toString());



                            ViewGroup grandParent = (ViewGroup)parent.getParent();
                            // Logger.toast_i_long(mActivity, grandParent.toString());
                            // Logger.toast_i_long(mActivity, grandParent.getChildCount() + " children");

                            /*
                            String names = "";
                            for(int _i = grandParent.getChildCount() -1; _i >=0; _i--){
                                final View _child = grandParent.getChildAt(_i);
                                _child.performClick();
                                _child.callOnClick();

                                names += _child.getClass().getName() + "|";

                                _child.setBackgroundColor(Color.RED);

                                _child.post(new Runnable(){
                                    @Override
                                    public void run() {
                                        _child.performClick();
                                        _child.callOnClick();
                                    }
                                });


                                Utils.performTouchOn(_child, parent, mActivity);



                            }
                            */

                            Logger.toast_i(mActivity, "touched:" + viewPager);
                            Utils.performTouchOn(child, viewPager, mActivity);

                            for (View v: viewPagerWithGridViews){
                                Utils.performTouchOn(child, v, mActivity);
                            }


                            // Logger.toast_i_long(mActivity, names);

                                        /*
                                        child.callOnClick();
                                        child.performClick();

                                        parent.callOnClick();
                                        parent.performClick();

                                        // Logger.toast_i_long(mActivity, parent.getChildCount() + " children");

                                        String names = "";
                                        for(int _i = parent.getChildCount() -1; _i >=0; _i--){
                                            final View _child = parent.getChildAt(i);
                                            _child.performClick();
                                            _child.callOnClick();

                                            int redColorValue = Color.RED;
                                            _child.setBackgroundColor(redColorValue);

                                            names += _child.getClass().getName() + "|";

                                            _child.post(new Runnable(){
                                                @Override
                                                public void run() {
                                                    _child.performClick();
                                                    _child.callOnClick();
                                                }
                                            });
                                        }
                                        Logger.toast_i_long(mActivity, names);
                                        */


                        }
                        if(title instanceof String &&
                                (title.equals("生鲜") || title.equals("京东到家") || title.equals("服装城"))){
                            // child.setVisibility(View.INVISIBLE);
                            child.setAlpha(0.2f);
                            parent.setAlpha(0.2f);
                        }
                    }
                } catch (SecurityException e) {

                } catch (NoSuchMethodException e) {

                } catch (InvocationTargetException e) {

                } catch (IllegalAccessException e) {

                }

            }


        });
        findAndHookActivity(MAIN_ACTIVITY, "onDestroy", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mActivity = (Activity) param.thisObject;

                // Utils.sendBroadcast2ExportedActivity(mActivity, "onDestroy");


            }
        });
    }

    private void testHook_JD_GRID_VIEW_PAGER_GRID_VIEW(){
        hookAllMethods(JD_GRID_VIEW_PAGER_GRID_VIEW, "onTouchEvent", new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object current = param.thisObject;

                String msg = current.toString() + Arrays.toString(param.args);
                // Logger.toast_i_long(mActivity, msg);
                Utils.printMsg2ExportedActivity(mActivity, msg);
            }
        });
    }

    // not work...
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

                Object current = param.thisObject;


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
    }

}
