package kr.co.enterprise1.mfpdemo.main;

import android.support.annotation.StringRes;

public interface LoginPresenter {

  void onSignInClick(String id, String pw);

  void onStart();

  void onPause();

  interface View {

    void showIdError(@StringRes int resId);

    void showPwError(@StringRes int resId);

    void showErrorSnackbar(String errorMsg, int remainingAttemps);

    void showErrorAlert(String title, String errorMsg);
  }
}
