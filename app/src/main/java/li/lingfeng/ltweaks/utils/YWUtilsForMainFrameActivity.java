package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yangwan on 08/06/2017.
 */

public class YWUtilsForMainFrameActivity {

    // 暂时不用了
    private static View viewPager = null;
    private static List<View> viewPagerWithGridViews = new ArrayList<View>();

    private static boolean alreadyDone = false;

    private static Activity tmpAct = null;
    public static RelativeLayout tmp京豆 = null;

    public static void tmpCallClick(){

    }

    public static void hackIt(Activity activity){

        if(alreadyDone) return;
        try{
            tmpAct = activity;
            // Utils.printClassMethods2ExportedActivity(mActivity, null);
            // Utils.printFields2ExportedActivity(mActivity, null);
            // Utils.printViewTree2ExportedActivity(mActivity, null);
            // Utils.sendBroadcast2ExportedActivity(mActivity, "onResume");
            View v = activity.findViewById(android.R.id.content);
            YWUtilsForMainFrameActivity.recursiveLoopChildren((ViewGroup) v);


        }catch (Exception e){
            Logger.e(e.getMessage());
            Logger.e("MainActivity onResume");
        }
    }

    public static void recursiveLoopChildren(ViewGroup parent) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                recursiveLoopChildren((ViewGroup) child);
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED

                // JDGridViewInViewPager
//                if( child.getClass().getSimpleName().equals("JDGridViewInViewPager") ){
                    // Logger.toast_i_long(mActivity, "Found JDGridViewInViewPager");

                    // com.jingdong.app.mall.home.floor.view.view.MallFloor_Icon.JDGridViewInViewPager;
                    // Logger.toast_i_long(mActivity, child.getClass().getCanonicalName());
                    // Utils.sendBroadcast2ExportedActivity(mActivity, "onResume");

//                    viewPager = child;
                    // printViewItSelf(mActivity, child);
//                }

            } else {
                if (child != null) {
//                    if( child.getClass().getSimpleName().equals("JDViewPagerWithGridView") ){
//                        viewPagerWithGridViews.add(child);
//                    }
                    // DO SOMETHING WITH VIEW
                    find京豆Widget(child, parent);
                    findAndHideWidgets(child, parent);
                }
            }
        }
    }

    // funny, er...
    private static void findAndHideWidgets(View child, ViewGroup parent){
        Object title = invokeNoParamMeth(child, "getText");
        if(null == title) return;
        if(title instanceof String &&
                (title.equals("生鲜") ||
                        title.equals("京东超市") ||
                        title.equals("京东到家") ||
                        title.equals("服装城") ||
                        title.equals("全球购"))){
            // child.setVisibility(View.INVISIBLE);
            changeAlpha2Low(child, parent);
            // alreadyDone = true;
        }
        if(title instanceof String &&
                (title.equals("二手清仓") ||
                        title.equals("沃尔玛") ||
                        title.equals("司法拍卖") ||
                        title.equals("机票火车票") ||
                        title.equals("物流查询") ||
                        title.equals("京东智能"))){
            changeAlpha2Low(child, parent);
        }

    }

    // 并没有成功...唉
    private static void find京豆Widget(View child, ViewGroup parent){

        Object title = invokeNoParamMeth(child, "getText");
        if(null == title) return;

        if(title instanceof String && title.equals("领京豆")){

            ////////////////////////////////////////////////////////////////////////
            // result: 10 个 parent是一个 JDGridViewInViewPager
            // ViewGroup grandParent = (ViewGroup)parent.getParent();
            // Logger.toast_i_long(mActivity, grandParent.toString());
            // Logger.toast_i_long(mActivity, grandParent.getChildCount() + " children");
            ////////////////////////////////////////////////////////////////////////

            try {
                tmp京豆 = (RelativeLayout) parent;
                // Logger.toast_i(tmpAct, "找到它了");
            }catch (Exception e){
                Logger.toast_i(tmpAct, "relativeLayout err");
            }

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
            Logger.toast_i(mActivity, "touched:" + viewPager);
            Utils.performTouchOn(child, viewPager, mActivity);

            for (View v: viewPagerWithGridViews){
                Utils.performTouchOn(child, v, mActivity);
            }
            */


            /*
            String names = "";
            for(int _i = parent.getChildCount() -1; _i >=0; _i--){
                final View _child = parent.getChildAt(i);
                _child.performClick();
                _child.callOnClick();

                int redColorValue = Color.RED;
                _child.setBackgroundColor(redColorValue);

                names += _child.getClass().getName() + "|";
            }
            Logger.toast_i_long(mActivity, names);
            */
        }
    }

    private static void changeAlpha2Low(View child, View parent){
        try{
            child.setAlpha(0.2f);
            parent.setAlpha(0.2f);
        }catch (Throwable throwable){
            Logger.e(throwable.getMessage());
        }
    }

    public static void invokeIt(Activity mActivity, Object mallFloor_Icon){
        if(null == YWUtilsForMainFrameActivity.tmp京豆){
            // Logger.toast_i(mActivity, "tmp 无");
            return;
        }

        // Logger.toast_i(mActivity, "tmp 有了!");
   

        YWUtilsForMainFrameActivity.invokeParamMeth(
                mallFloor_Icon,
                "access$000",
                mallFloor_Icon,
                YWUtilsForMainFrameActivity.tmp京豆,
                6,
                mActivity);
    }


    // todo para object can be mutiple
    // https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
    public static Object invokeNoParamMeth(Object obj, String methodName){
        java.lang.reflect.Method method = null;
        try {
            method = obj.getClass().getMethod(methodName);
        } catch (SecurityException e) {
            Logger.stackTrace(e);
        }  catch (NoSuchMethodException e) {
            Logger.stackTrace(e);
        }
        try {
            if (method != null) {
                return method.invoke(obj);
            }else{
                return null;
            }
        } catch (IllegalArgumentException e) {
            Logger.stackTrace(e);
        } catch (IllegalAccessException e) {
            Logger.stackTrace(e);
        } catch (InvocationTargetException e) {
            Logger.stackTrace(e);
        }
        return null;
    }



    // .method static synthetic access$000(Lcom/jingdong/app/mall/home/floor/view/view/MallFloor_Icon;Landroid/view/View;I)V
    public static void invokeParamMeth(Object obj,
                                       String methodName,
                                       Object mallFloor_Icon,
                                       RelativeLayout input_layout,
                                       int position,
                                       Activity act){

        java.lang.reflect.Method method = null;
        try {
            method = obj.getClass().getMethod(methodName, mallFloor_Icon.getClass(), android.view.View.class, int.class);
        } catch (SecurityException | NoSuchMethodException e) {
            Logger.stackTrace(e);
            try {
                Logger.toast_i(act, "e:" + e.getCause());
            }catch (Exception e1){}
            YWUtilsLogger.printMsg2ExportedActivity(act, Arrays.toString(e.getStackTrace()));
        }
        try {
            if (method != null) {
                method.invoke(null, mallFloor_Icon, input_layout, position);

            }else{
                Logger.toast_i(act, "method null");
            }
        } catch (IllegalArgumentException e) {
            Logger.stackTrace(e);
        } catch (IllegalAccessException e) {
            Logger.stackTrace(e);
        } catch (InvocationTargetException e) {
            Logger.stackTrace(e);
        }

    }


}
