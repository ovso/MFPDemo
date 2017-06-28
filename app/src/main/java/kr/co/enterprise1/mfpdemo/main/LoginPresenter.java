package kr.co.enterprise1.mfpdemo.main;

import kr.co.enterprise1.mfpdemo.main.vo.VersionCheck;

interface LoginPresenter {

  void onLoginClick(String id, String pw);

  void onStart();

  void onPause();

  void onCreate();

  void onUpdateClick();

  interface View {

    void showIdError(String msg);

    void showPwError(String msg);

    void showErrorSnackbar(String errorMsg, int remainingAttemps);

    void showErrorSnackbar(String errorMsg);

    void showErrorAlert(String title, String errorMsg);

    void navigateToHome();

    void showLoading();

    void hideLoading();

    void showUpdateAlert(VersionCheck version);

    void navigateToExternalAppCenter();

    void exitApp();

    void showNotificationsAlert(String title, String message);
  }
}
