package kr.co.enterprise1.mfpdemo.main;

import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.app.MyApplication;

public class LoginPresenterImpl implements LoginPresenter {
  private final static String TAG = "LoginPresenterImpl";
  private LoginPresenter.View view;
  private LoginInteractor loginInteractor;

  public LoginPresenterImpl(LoginPresenter.View view) {
    this.view = view;
    loginInteractor = new LoginInteractor(MyApplication.getContext());
    loginInteractor.setOnLoginResultListener(onLoginResultListener);
  }

  private LoginInteractor.OnLoginResultListener onLoginResultListener =
      new LoginInteractor.OnLoginResultListener() {
        @Override public void onLoginSuccess() {

        }

        @Override public void onLoginFailure(String errorMsg) {
          view.showErrorAlert("!", errorMsg);
        }

        @Override public void onLoginRequired(String errorMsg, int remaningAttempts) {
          view.showErrorSnackbar(errorMsg, remaningAttempts);
        }
      };

  @Override public void onSignInClick(String id, String pw) {
    boolean isIdEmpty = id.isEmpty();
    boolean isPwEmpty = pw.isEmpty();
    if (isIdEmpty) {
      view.showIdError(R.string.erroe_require_id);
    } else {
      view.showIdError(0);
    }
    if (isPwEmpty) {
      view.showPwError(R.string.error_require_pw);
    } else {
      view.showPwError(0);
    }
    if (!(isIdEmpty && isPwEmpty)) {
      loginInteractor.login(id, pw);
    }
  }

  @Override public void onStart() {
    loginInteractor.registerReceiver();
  }

  @Override public void onPause() {
    loginInteractor.unRegisterReceiver();
  }
}