package li.lingfeng.ltweaks.ywhook;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by yangwan on 14/06/2017.
 */

public class YWUtilsForWechat {


    public static View recursiveLoopChildrenFind(ViewGroup parent, String target) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                View foundIt = recursiveLoopChildrenFind((ViewGroup) child, target);
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
                if(null != foundIt){
                    return foundIt;
                }
            } else {
                if (child != null) {
                    // DO SOMETHING WITH VIEW
                    Object title = YWUtilsForMainFrameActivity.invokeNoParamMeth(child, "getText");
                    if(null == title) continue;

//                            if(title instanceof String && title.equals("详情")){
//                                int redColorValue = Color.RED;
//                                child.setBackgroundColor(redColorValue);
//                            }
                    if(title instanceof String && title.equals(target)){
                        return child;
                    }
//                            if(title instanceof String && title.equals("评价")){
//                                int redColorValue = Color.RED;
//                                child.setBackgroundColor(redColorValue);
//                            }
                }
            }
        }
        return null;
    }
    public static String SwipeBackLayout = "com.tencent.mm.ui.widget.SwipeBackLayout";
    // 并没有找到:
    // com.tencent.mm.ui.widget.SwipeBackLayout
    // 难道它用的是fragments? (如何找到所有的fragments)
    public static View recursiveLoopChildrenFindType(ViewGroup parent, String targetClassString){
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                View foundIt = recursiveLoopChildrenFindType((ViewGroup) child, targetClassString);
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
                if(null != foundIt){
                    return foundIt;
                }
            } else {
                if (child != null) {
                    Logger.i(child.getClass().getSimpleName());
                    Logger.i(child.getClass().getCanonicalName());
                    if(child.getClass().getCanonicalName().equals(targetClassString)){
                        int redColorValue = Color.RED;
                        child.setBackgroundColor(redColorValue);
                        return child;
                    }
                }
            }
        }
        return null;
    }

    public static Object findFieldRef(Object target, String targetClassFullName){

        // current class' public & private fields
        Field[] fields = target.getClass().getDeclaredFields();
        for(Field f:fields){

            if(targetClassFullName.equals(f.getType().getCanonicalName())){
                try {
                    // Logger.i(f.getType().getSimpleName());
                    // Logger.i(f.getType().getCanonicalName());
                    // Logger.i(f.isAccessible() + "");
                    f.setAccessible(true);
                    // Logger.i(f.isAccessible() + "");

                    // 它是一个null, 呃...
                    Object foundIt = f.get(target);
                    // Logger.i(foundIt.toString());

                    return foundIt;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
