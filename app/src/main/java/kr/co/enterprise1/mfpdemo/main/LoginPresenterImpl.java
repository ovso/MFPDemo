package kr.co.enterprise1.mfpdemo.main;

import android.content.Context;
import com.worklight.wlclient.api.WLClient;

class LoginPresenterImpl implements LoginPresenter {
  //private final static String TAG = "LoginPresenterImpl";
  private LoginPresenter.View view;
  private LoginInteractor loginInteractor;
  private CredentialsInputHandler credentialsInputHandler;

  LoginPresenterImpl(LoginPresenter.View view) {
    this.view = view;
    Context context = WLClient.getInstance().getContext();
    loginInteractor = new LoginInteractor(context);
    loginInteractor.setOnLoginResultListener(onLoginResultListener());
    credentialsInputHandler = new CredentialsInputHandler(context);
    credentialsInputHandler.setOnInputResultListener(onInputResultListener());
  }

  private CredentialsInputHandler.OnInputResultListener onInputResultListener() {
    return new CredentialsInputHandler.OnInputResultListener() {
      @Override public void idError(String msg) {
        view.showIdError(msg);
      }

      @Override public void pwError(String msg) {
        view.showPwError(msg);
      }

      @Override public void pass(String id, String pw) {
        view.showLoading();
        loginInteractor.login(id, pw);
      }
    };
  }

  private LoginInteractor.OnLoginResultListener onLoginResultListener() {
    return new LoginInteractor.OnLoginResultListener() {
      @Override public void onLoginSuccess() {
        view.navigateToHome();
      }

      @Override public void onLoginFailure(String errorMsg) {
        view.showErrorAlert("!", errorMsg);
      }

      @Override public void onLoginRequired(String errorMsg, int remaningAttempts) {
        view.showErrorSnackbar(errorMsg, remaningAttempts);
      }

      @Override public void onLoginFinished() {
        view.hideLoading();
      }
    };
  }

  @Override public void onLoginClick(String id, String pw) {
    credentialsInputHandler.login(id, pw);
  }

  @Override public void onStart() {
    loginInteractor.registerReceiver();
  }

  @Override public void onPause() {
    loginInteractor.unregisterReceiver();
  }
}