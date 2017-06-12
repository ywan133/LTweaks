package li.lingfeng.ltweaks.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by yangwan on 12/06/2017.
 */

public class WYHookie {

    private Activity activity;
    private ViewGroup rootView;

    // 用来防止其 卡顿
    private int runOnResumeHookTimes = 3;
    private int runOnLayoutHookTimes = 3;

    public RelativeLayout tmp京豆RelativeLayout = null;

    public WYHookie(XC_MethodHook.MethodHookParam param) {
        activity = (Activity) param.thisObject;
        rootView = (ViewGroup) activity.findViewById(android.R.id.content);
    }

    public void hookOnResume(){
        try{

            if(runOnResumeHookTimes > 0){
                recursiveLoopChildren(rootView);
                runOnResumeHookTimes--;
            }
            if(0 == runOnResumeHookTimes){
                Logger.toast_i(activity, "onResume 次数用完");
            }

        }catch (Throwable t){
            YWUtilsLogger.printMsg2ExportedActivity(activity, t);
        }

    }

    public void hookOnLayout(){
        try{

            if(runOnLayoutHookTimes > 0){
                recursiveLoopChildren(rootView);
                runOnLayoutHookTimes--;
            }
            if(0 == runOnLayoutHookTimes){
                Logger.toast_i(activity, "onLayout 次数用完");
            }

        }catch (Throwable t){
            YWUtilsLogger.printMsg2ExportedActivity(activity, t);
        }
    }

    public void hookOnDestroy(){
        try{
            this.activity = null;
            this.rootView = null;
        }catch (Throwable t){
            YWUtilsLogger.printMsg2ExportedActivity(activity, t);
        }
    }

    private void recursiveLoopChildren(ViewGroup parent) {
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
    private void findAndHideWidgets(View child, ViewGroup parent){
        Object title = YWUtilsForMainFrameActivity.invokeNoParamMeth(child, "getText");
        if(null == title) return;
        if(title instanceof String &&
                (title.equals("生鲜") ||
                        title.equals("京东超市") ||
                        title.equals("京东到家") ||
                        title.equals("服装城") ||
                        title.equals("全球购"))){
            // child.setVisibility(View.INVISIBLE);
            changeAlpha2Low(child, parent);
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
    private void find京豆Widget(View child, ViewGroup parent){

        Object title = YWUtilsForMainFrameActivity.invokeNoParamMeth(child, "getText");
        if(null == title) return;

        if(title instanceof String && title.equals("领京豆")){

            ////////////////////////////////////////////////////////////////////////
            // result: 10 个 parent是一个 JDGridViewInViewPager
            // ViewGroup grandParent = (ViewGroup)parent.getParent();
            // Logger.toast_i_long(mActivity, grandParent.toString());
            // Logger.toast_i_long(mActivity, grandParent.getChildCount() + " children");
            ////////////////////////////////////////////////////////////////////////

            try {
                tmp京豆RelativeLayout = (RelativeLayout) parent;
                // Logger.toast_i(tmpAct, "找到它了");
            }catch (Exception e){
                Logger.toast_i(activity, "relativeLayout err");
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

    private void changeAlpha2Low(View child, View parent){
        try{
            child.setAlpha(0.2f);
            parent.setAlpha(0.2f);
        }catch (Throwable throwable){
            Logger.e(throwable.getMessage());
        }
    }


}
