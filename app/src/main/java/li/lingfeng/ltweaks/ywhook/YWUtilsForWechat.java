package li.lingfeng.ltweaks.ywhook;

import android.view.View;
import android.view.ViewGroup;

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
}
