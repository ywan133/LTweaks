package li.lingfeng.ltweaks.xposed.shopping;

import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.lib.XposedLoad;
import li.lingfeng.ltweaks.prefs.PackageNames;
import li.lingfeng.ltweaks.utils.Logger;
import li.lingfeng.ltweaks.xposed.XposedBase;

/**
 * Created by yangwan on 04/06/2017.
 */
@XposedLoad(packages = PackageNames.JD, prefs = R.string.key_jd_sign_in_auto)
public class XposedAutoSignInClip extends XposedBase {

    @Override
    protected void handleLoadPackage() throws Throwable {
        Logger.i("wy: ready to hook sign-sin-auto");

    }

}
