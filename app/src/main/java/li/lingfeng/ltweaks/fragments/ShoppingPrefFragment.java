package li.lingfeng.ltweaks.fragments;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;

import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.activities.JDActivity;
import li.lingfeng.ltweaks.activities.JDHistoryActivity;
import li.lingfeng.ltweaks.lib.PreferenceChange;
import li.lingfeng.ltweaks.utils.ComponentUtils;
import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by smallville on 2016/12/25.
 */

public class ShoppingPrefFragment extends BasePrefFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_shopping);
    }

    @PreferenceChange(prefs = R.string.key_jd_open_link_in_app)
    private void enableJdOpenLinkInApp(Preference preference, boolean enabled) {
        Logger.toast_i(getActivity(), "enableJdOpenLinkInApp");

        ComponentUtils.enableComponent(JDActivity.class, enabled);
    }

    @PreferenceChange(prefs = { R.string.key_jd_history, R.string.key_suning_history })
    private void enableJdHistory(Preference preference, boolean enabled) {
        Logger.toast_i(getActivity(), "enableJdHistory");

        ComponentUtils.enableComponent(JDHistoryActivity.class, enabled);
        findSwitchPreference(R.string.key_jd_history).setChecked(enabled);
        findSwitchPreference(R.string.key_suning_history).setChecked(enabled);
    }

    @PreferenceChange(prefs = { R.string.key_jd_sign_in_auto})
    private void enableJdSignInAuto(Preference preference, boolean enabled) {
        Logger.toast_i(getActivity(), "enableJdSignInAuto");

        // ComponentUtils.enableComponent(JDHistoryActivity.class, enabled);
        findSwitchPreference(R.string.key_jd_sign_in_auto).setChecked(enabled);
    }

}
