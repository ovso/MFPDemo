package kr.co.enterprise1.mfpdemo.setting;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import kr.co.enterprise1.mfpdemo.R;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingFragment extends PreferenceFragment implements SettingFragmentPresenter.View {
  private SettingFragmentPresenter mPresenter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences_setting);
    mPresenter = new SettingFragmentPresenterImpl(this);
    mPresenter.onCreatePreferences(findPreference("notifications"));
  }

  private SwitchPreference mNotificationsPreference;
  private MultiSelectListPreference mTagsNotificationsListPreference;

  @Override public void addListener() {
    mNotificationsPreference = (SwitchPreference) findPreference("notifications");
    mNotificationsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
      mPresenter.onNotificationsPreferenceChange(Boolean.valueOf(String.valueOf(newValue)));
      return true;
    });
    mTagsNotificationsListPreference = (MultiSelectListPreference) findPreference("tags");
    mTagsNotificationsListPreference.setOnPreferenceChangeListener((preference, newValue) -> {
      mPresenter.onTagsNotificationsPreferencChange(newValue);
      return true;
    });
    if (!mNotificationsPreference.isChecked()) {
      disableTags();
    }
  }

  @Override public void hideLoading() {
    if (mLoading != null) {
      mLoading.dismiss();
      mLoading = null;
    }
  }

  private ProgressDialog mLoading;

  @Override public void showLoading() {
    mLoading = ProgressDialog.show(getActivity(), "", "");
    mLoading.setContentView(R.layout.loading);
    mLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  }

  @Override public void disableTags() {
    Runnable run = () -> mTagsNotificationsListPreference.setEnabled(false);
    getActivity().runOnUiThread(run);
  }

  @Override public void enableTags() {
    Runnable run = () -> mTagsNotificationsListPreference.setEnabled(true);
    getActivity().runOnUiThread(run);
  }

  @Override public void setTagEntries(String[] tags) {
    mTagsNotificationsListPreference.setEntries(tags);
    mTagsNotificationsListPreference.setEntryValues(tags);
  }

  @Override public void setTagSummary(String summary) {
    Runnable run = () -> mTagsNotificationsListPreference.setSummary(summary);
    getActivity().runOnUiThread(run);
  }

  @Override public void setVersionName(String title) {
    Preference screen = findPreference("version");
    screen.setTitle(title);
  }

  @Override public void showNotificationsAlert(String title, String message) {
    new AlertDialog.Builder(getActivity()).setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showOnSwitch() {
    Runnable run = () -> {
      mNotificationsPreference.setChecked(true);
      mNotificationsPreference.setSummary(R.string.ok_notifications);
    };
    getActivity().runOnUiThread(run);
  }

  @Override public void showOffSwitch() {
    Runnable run = () -> {
      mNotificationsPreference.setChecked(false);
      mNotificationsPreference.setSummary(R.string.not_notifications);
    };
    getActivity().runOnUiThread(run);
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
