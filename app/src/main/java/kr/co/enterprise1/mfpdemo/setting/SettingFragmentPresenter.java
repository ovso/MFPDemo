package kr.co.enterprise1.mfpdemo.setting;

import java.util.List;

/**
 * Created by jaeho on 2017. 6. 28
 */

public interface SettingFragmentPresenter {
  void onCreatePreferences();

  void onNotificationsPreferenceChange(boolean value);

  void onPause();

  void onResume();

  void onTagsNotificationsPreferencChange(Object object);

  interface View {

    void showNotificationsAlert(String title, String message);

    void showOnSwitch();

    void showOffSwitch();

    void addListener();

    void hideLoading();

    void showLoading();

    void disableTags();

    void enableTags();

    void setTagEntries(String[] tags);
  }
}
