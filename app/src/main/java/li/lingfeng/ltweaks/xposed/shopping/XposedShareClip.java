package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.utils.ShareUtils;
import li.lingfeng.ltweaks.xposed.XposedBase;
import li.lingfeng.ltweaks.ywhook.YWUtilsForJdPrice;
import li.lingfeng.ltweaks.ywhook.YWUtilsForMainFrameActivity;
import li.lingfeng.ltweaks.ywhook.YWUtilsLogger;

/**
 * Created by smallville on 2017/5/31.
 */

public abstract class XposedShareClip extends XposedBase {

    private Activity mActivity;
    private View 商品View;
    private View 详情View;
    private boolean mIsSharing = false;
    private String foundProductId = "";


    @Override
    protected void handleLoadPackage() throws Throwable {

        findAndHookMethod(ClipboardManager.class, "setPrimaryClip", ClipData.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                if (isSharing() && mActivity != null) {
                    Logger.i("ClipboardManager setPrimaryClip" + param.args[0]);
                    ClipData clipData = (ClipData) param.args[0];

                    ShareUtils.shareClipWithSnackbar(mActivity, clipData);
                }
            }
        });

        findAndHookActivity(getItemActivity(), "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                mActivity = (Activity) param.thisObject;

                // just in case if it is not jing-dong
                try {
                    Intent intent = mActivity.getIntent();
                    if (null != intent && null != intent.getExtras()) {
                        foundProductId = intent.getExtras().get("id").toString();
                        Logger.toast_i(mActivity, "id:" + foundProductId);
                    }
                    ViewGroup rootView = (ViewGroup) mActivity.findViewById(android.R.id.content);
                    商品View = YWUtilsForJdPrice.hookALongClickBtn(rootView, foundProductId, mActivity, 商品View);
                    详情View = YWUtilsForJdPrice.hookALongClickBtn详情(rootView, foundProductId, mActivity, 详情View);
                    商品View = null;
                    详情View = null;
                }catch (Throwable t){
                    Logger.toast_i(mActivity, "jd-price-history");
                }
            }
        });
        findAndHookActivity(getItemActivity(), "refreshView", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                try {
                    Bundle extra = (Bundle) param.args[1];
                    if (null != extra ) {
                        foundProductId = extra.get("id").toString();
                        Logger.toast_i(mActivity, "id(refreshView):" + foundProductId);
                    }
                }catch (Throwable t){
                    Logger.toast_i(mActivity, "jd-price-history refreshView");
                }
            }
        });
        findAndHookActivity(getItemActivity(), "onSaveInstanceState", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    Bundle extra = (Bundle) param.args[0];
                    if (null != extra ) {
                        foundProductId = extra.get("id").toString();
                        Logger.toast_i(mActivity, "id(onSaveInstanceState):" + foundProductId);
                    }
                }catch (Throwable t){
                    Logger.toast_i(mActivity, "jd-price-history onSaveInstanceState");
                }
            }
        });

        findAndHookActivity(getItemActivity(), "getPageParam", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object result = param.getResult();
                Logger.toast_i(mActivity, "id(getPageParam):" + result);
            }
        });

        findAndHookActivity(getItemActivity(), "onDestroy", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mActivity = null;
                商品View = null;
                详情View = null;
                YWUtilsForJdPrice.onDestroy();
                Logger.toast_i(mActivity, "ProductDetailActivity: destory");
            }
        });

        findAndHookActivity(getItemActivity(), "onStop", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                Logger.toast_i(mActivity, "ProductDetailActivity: stop");
                mActivity = null;
                商品View = null;
                详情View = null;
            }
        });
        findAndHookActivity(getItemActivity(), "onPause", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                Logger.toast_i(mActivity, "ProductDetailActivity: pause");
                mActivity = null;
                商品View = null;
                详情View = null;
            }
        });


        findAndHookActivity(getItemActivity(), "onCreateOptionsMenu", Menu.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Logger.toast_i(mActivity, "onCreateOptionsMenu");

            }
        });


        if (getShareActivity() != null) {
            findAndHookActivity(getShareActivity(), "onResume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Logger.i("Share activity onResume.");
                    mIsSharing = true;
                    // Logger.toast_i(mActivity, "ShareActivity: share act resume");
                }
            });

            findAndHookActivity(getShareActivity(), "onPause", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Logger.i("Share activity onPause.");
                    mIsSharing = false;
                    // Logger.toast_i(mActivity, "ShareActivity: share act pause");
                }
            });
        }


    }

    protected abstract String getItemActivity();

    protected String getShareActivity() {
        return null;
    }

    private boolean isSharing() {
        return getShareActivity() == null ? true : mIsSharing;
    }
}
