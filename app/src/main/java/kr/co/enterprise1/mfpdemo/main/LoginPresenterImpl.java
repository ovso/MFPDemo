package kr.co.enterprise1.mfpdemo.main;

import kr.co.enterprise1.mfpdemo.app.MyApplication;

public class LoginPresenterImpl implements LoginPresenter {
  private final static String TAG = "LoginPresenterImpl";
  private LoginPresenter.View view;
  private LoginInteractor loginInteractor;
  private CredentialsInputHandler credentialsInputHandler;

  public LoginPresenterImpl(LoginPresenter.View view) {
    this.view = view;
    loginInteractor = new LoginInteractor(MyApplication.getContext());
    loginInteractor.setOnLoginResultListener(onLoginResultListener);
    credentialsInputHandler = new CredentialsInputHandler(MyApplication.getContext());
    credentialsInputHandler.setOnInputResultListener(onInputResultListener);
  }

  private CredentialsInputHandler.OnInputResultListener onInputResultListener =
      new CredentialsInputHandler.OnInputResultListener() {
        @Override public void idError(String msg) {
          view.showIdError(msg);
        }

        @Override public void pwError(String msg) {
          view.showPwError(msg);
        }

        @Override public void pass(String id, String pw) {
          loginInteractor.login(id, pw);
        }
      };

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

  @Override public void onLoginClick(String id, String pw) {
    credentialsInputHandler.login(id, pw);
  }

  @Override public void onStart() {
    loginInteractor.registerReceiver();
  }

  @Override public void onPause() {
    loginInteractor.unRegisterReceiver();
  }
}