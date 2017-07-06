package kr.co.enterprise1.mfpdemo.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.squareup.otto.Subscribe;
import com.worklight.wlclient.api.WLAuthorizationManager;
import kr.co.enterprise1.mfpdemo.eventbus.BusProvider;
import kr.co.enterprise1.mfpdemo.eventbus.LoginEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginFailureEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginRequiredEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginSuccessEvent;
import lombok.Setter;

class LoginRequestInteractor {
  private final static String TAG = "LoginRequestInteractor";

  private LocalBroadcastManager localBroadcastManager;

  LoginRequestInteractor(Context context) {
    this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
  }

  private BroadcastReceiver loginErrorReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      onLoginResultListener.onLoginFailure(intent.getStringExtra("errorMsg"));
      onLoginResultListener.onLoginFinished();
    }
  };
  private BroadcastReceiver loginRequiredReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      onLoginResultListener.onLoginRequired(intent.getStringExtra("errorMsg"),
          intent.getIntExtra("remainingAttempts", -1));
      onLoginResultListener.onLoginFinished();
    }
  };
  private BroadcastReceiver loginSuccessReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      onLoginResultListener.onLoginSuccess();
      onLoginResultListener.onLoginFinished();
    }
  };

  void registerReceiver() {
    BusProvider.getInstance().register(this);
  }

  void unregisterReceiver() {
    BusProvider.getInstance().unregister(this);
  }

  @Subscribe public void onLoginRequiredEvent(LoginRequiredEvent event) {
    onLoginResultListener.onLoginRequired(event.getErrorMsg(), event.getRemainingAttempts());
    onLoginResultListener.onLoginFinished();
  }

  @Subscribe public void onLoginSuccessEvent(LoginSuccessEvent event) {
    onLoginResultListener.onLoginSuccess();
    onLoginResultListener.onLoginFinished();
  }

  @Subscribe public void onLoginFailureEvent(LoginFailureEvent event) {
    onLoginResultListener.onLoginFailure(event.getErrorMsg());
    onLoginResultListener.onLoginFinished();
  }

  void login(String id, String pw) {
    BusProvider.getInstance().post(new LoginEvent(id, pw));
  }

  @Setter private OnLoginResultListener onLoginResultListener;

  public void autoLogin() {
    WLAuthorizationManager.getInstance().obtainAccessToken(null, null);
  }

  interface OnLoginResultListener {
    void onLoginSuccess();

    void onLoginFailure(String errorMsg);

    void onLoginRequired(String errorMsg, int remaningAttempts);

    void onLoginFinished();
  }
}
