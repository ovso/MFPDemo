package kr.co.enterprise1.mfpdemo.main;

import android.support.annotation.StringRes;

public interface LoginPresenter {

  void onLoginClick(String id, String pw);

  void onStart();

  void onPause();

  interface View {

    void showIdError(String msg);

    void showPwError(String msg);

    void showErrorSnackbar(String errorMsg, int remainingAttemps);

    void showErrorAlert(String title, String errorMsg);
  }
}
