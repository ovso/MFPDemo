package kr.co.enterprise1.mfpdemo.home;


interface HomePresenter {

  void onCreate();

  void onGetBanlanceClick();

  void onLogoutClick();

  void onStart();

  void onPause();

  void onBackPressed();

  void onLogoutDialogOkClick();

  void onDestroy();

  interface View {
    void showHello(String text);

    void navigateToLogin();

    void showLogoutAlert(String title, String message);

    void showNotificationsAlert(String title, String message);

    void activityFinish();
  }
}
