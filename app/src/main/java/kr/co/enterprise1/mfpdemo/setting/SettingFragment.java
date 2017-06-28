package kr.co.enterprise1.mfpdemo.setting;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import kr.co.enterprise1.mfpdemo.R;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingFragment extends PreferenceFragmentCompat {
  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences_setting, null);
  }
}
