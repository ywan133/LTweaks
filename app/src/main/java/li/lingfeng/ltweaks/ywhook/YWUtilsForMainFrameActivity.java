package li.lingfeng.ltweaks.ywhook;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by yangwan on 08/06/2017.
 */

public class YWUtilsForMainFrameActivity {

    // 暂时不用了
    private static View viewPager = null;
    private static List<View> viewPagerWithGridViews = new ArrayList<View>();

    private static RelativeLayout tmp京豆;


    @Deprecated
    public static void invokeIt(Activity mActivity, Object mallFloor_Icon){
        if(null == YWUtilsForMainFrameActivity.tmp京豆){
            // Logger.toast_i(mActivity, "tmp 无");
            return;
        }


   

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

    public static void invokeClickTmp(Object obj,
                                      String methodName,
                                      RelativeLayout input_layout,
                                      int position,
                                      Activity act){
        java.lang.reflect.Method method = null;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, android.view.View.class, int.class);
            method.setAccessible(true);
        } catch (SecurityException | NoSuchMethodException e) {
            Logger.stackTrace(e);
            try {
                Logger.toast_i(act, "e:" + e.getCause());
            }catch (Exception e1){}
            YWUtilsLogger.printMsg2ExportedActivity(act, Arrays.toString(e.getStackTrace()));
        }

        try {
            if (method != null) {
                method.invoke(obj, input_layout, position);

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
