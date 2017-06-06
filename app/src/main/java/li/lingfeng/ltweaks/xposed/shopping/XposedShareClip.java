package li.lingfeng.ltweaks.xposed.shopping;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.utils.ShareUtils;
import li.lingfeng.ltweaks.xposed.XposedBase;

/**
 * Created by smallville on 2017/5/31.
 */

public abstract class XposedShareClip extends XposedBase {

    private Activity mActivity;
    private boolean mIsSharing = false;



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

                Intent intent = mActivity.getIntent();


                String foundProductId = "";
                if(null != intent){
                    if(null != intent.getExtras()){
                        foundProductId = intent.getExtras().get("id").toString();
                        Logger.toast_i(mActivity, "id:" + foundProductId);
                    }else{

                    }
                }

                /*
                Intent _intent = new Intent();
                _intent.setClassName(
                        // Your app's package name
                        "li.lingfeng.ltweaks",
                        // The full class name of the activity you want to start
                        "li.lingfeng.ltweaks.activities.WYViewHierarchyActivity");
                _intent.setType("data/view");
                _intent.putExtra(Intent.EXTRA_TEXT, intent.getExtras().toString());
                mActivity.startActivity(_intent);
                */

                Intent _fuck_intent = new Intent(Intent.ACTION_SEND);
                _fuck_intent.setType("text/plain");
                _fuck_intent.putExtra(Intent.EXTRA_TEXT, foundProductId);
                // mActivity.startActivity(_fuck_intent);

                /*
                Field[] fields = mActivity.getClass().getFields();
                String str = "";
                for(Field f:fields){
                    str = str + f.getName() + ";";
                }
                Logger.toast_i(mActivity, str);
                */

            }
        });

        findAndHookActivity(getItemActivity(), "onStop", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                // Logger.toast_i(mActivity, "ProductDetailActivity: stop");
                mActivity = null;
            }
        });



        if (getShareActivity() != null) {
            findAndHookActivity(getShareActivity(), "onResume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Logger.i("Share activity onResume.");
                    mIsSharing = true;
                     Logger.toast_i(mActivity, "ShareActivity: share act resume");
                }
            });

            findAndHookActivity(getShareActivity(), "onPause", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Logger.i("Share activity onPause.");
                    mIsSharing = false;
                     Logger.toast_i(mActivity, "ShareActivity: share act pause");
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
