package kr.co.enterprise1.mfpdemo.main;

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

    void showErrorAlert(String title, String errorMsg);

    void navigateToHome();

    void showLoading();

    void hideLoading();

    void showUpdateAlert(String title, String message);

    void navigateToExternalAppCenter();
  }
}
