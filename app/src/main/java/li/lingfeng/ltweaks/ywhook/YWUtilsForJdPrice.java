package li.lingfeng.ltweaks.ywhook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yangwan on 13/06/2017.
 */

public class YWUtilsForJdPrice {



    public static View hookALongClickBtn(ViewGroup rootView,
                                         final String foundProductId,
                                         final Activity mActivity,
                                         View child){
        if(null == child) {
            child = recursiveLoopChildren(rootView, "商品");
        }

        int redColorValue = Color.RED;
        child.setBackgroundColor(redColorValue);

        child.setClickable(true);
        child.setLongClickable(true);
        child.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Logger.toast_i_long(mActivity, "long click");

                YWUtilsForJdPrice.invokeJdPriceHistory(foundProductId, mActivity);
                return false;
            }
        });
        return child;

    }

    public static View hookALongClickBtn详情(ViewGroup rootView,
                                         final String foundProductId,
                                         final Activity mActivity,
                                         View child){
        if(null == child) {
            child = recursiveLoopChildren(rootView, "详情");
        }

        int redColorValue = Color.GREEN;
        child.setBackgroundColor(redColorValue);

        child.setClickable(true);
        child.setLongClickable(true);
        child.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                YWUtilsLogger.printClassMethods2ExportedActivity(mActivity, null);
                return false;
            }
        });
        return child;

    }

    private static View recursiveLoopChildren(ViewGroup parent, String target) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                View foundIt = recursiveLoopChildren((ViewGroup) child, target);
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

    private static void invokeJdPriceHistory(String foundProductId, Activity mActivity){

        JSONObject node = new JSONObject();
        try {
            node.put("product_id", foundProductId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setClassName(
                // Your app's package name
                "li.lingfeng.ltweaks",
                // The full class name of the activity you want to start
                "li.lingfeng.ltweaks.activities.JDHistoryActivity");
        intent.setType("data/view");

        intent.putExtra(Intent.EXTRA_TEXT, node.toString());
        mActivity.startActivity(intent);

    }

    public static void onDestroy() {

    }
}
