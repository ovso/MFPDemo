package kr.co.enterprise1.mfpdemo.home;


interface HomePresenter {

  void onCreate();

  void onLogoutClick();

  void onStart();

  void onPause();

  void onBackPressed();

  void onLogoutDialogOkClick();

  void onDestroy();

  void onSettingClick();

  interface View {
    void showHello(String text);

    void navigateToLogin();

    void showLogoutAlert(String title, String message);

    void showNotificationsAlert(String title, String message);

    void activityFinish();

    void navigateToSetting();
  }
}
