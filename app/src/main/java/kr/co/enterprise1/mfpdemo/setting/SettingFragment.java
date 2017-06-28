package kr.co.enterprise1.mfpdemo.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import kr.co.enterprise1.mfpdemo.R;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingFragment extends PreferenceFragmentCompat
    implements SettingFragmentPresenter.View {
  private SettingFragmentPresenter mPresenter;

  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences_setting, null);
    Preference notificationsPreference = findPreference("notifications");
    notificationsPreference.setOnPreferenceChangeListener(onPreferenceChangeListener());
    mPresenter = new SettingFragmentPresenterImpl(this);
    mPresenter.onCreatePreferences();
  }

  private Preference.OnPreferenceChangeListener onPreferenceChangeListener() {
    return (preference, newValue) -> {
      mPresenter.onPreferenceChange(Boolean.valueOf(String.valueOf(newValue)));
      return true;
    };
  }

  @Override public void showNotificationsAlert(String title, String message) {
    new AlertDialog.Builder(getActivity()).setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show();
    //Toast.makeText(getContext(), title + ", " + message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showOnSwitch() {
    SwitchPreferenceCompat switchPreferenceCompat =
        (SwitchPreferenceCompat) findPreference("notifications");
    switchPreferenceCompat.setChecked(true);
  }

  @Override public void showOffSwitch() {
    SwitchPreferenceCompat switchPreferenceCompat =
        (SwitchPreferenceCompat) findPreference("notifications");
    switchPreferenceCompat.setChecked(false);
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override public void onPause() {
    super.onPause();
    mPresenter.onPause();
  }
}
