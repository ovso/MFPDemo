package kr.co.enterprise1.mfpdemo.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import kr.co.enterprise1.mfpdemo.common.Constants;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

class LoginInteractor {
  private final static String TAG = "LoginInteractor";

  private LocalBroadcastManager localBroadcastManager;

  LoginInteractor(Context context) {
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
    localBroadcastManager.registerReceiver(loginRequiredReceiver,
        new IntentFilter(Constants.ACTION_LOGIN_REQUIRED));
    localBroadcastManager.registerReceiver(loginErrorReceiver,
        new IntentFilter(Constants.ACTION_LOGIN_FAILURE));
    localBroadcastManager.registerReceiver(loginSuccessReceiver,
        new IntentFilter(Constants.ACTION_LOGIN_SUCCESS));
  }

  void unregisterReceiver() {
    localBroadcastManager.unregisterReceiver(loginRequiredReceiver);
    localBroadcastManager.unregisterReceiver(loginErrorReceiver);
    localBroadcastManager.unregisterReceiver(loginSuccessReceiver);
  }

  void login(String id, String pw) {
    JSONObject credentials = new JSONObject();
    try {
      credentials.put("username", id);
      credentials.put("password", pw);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    Intent intent = new Intent(Constants.ACTION_LOGIN);
    intent.putExtra("credentials", credentials.toString());
    localBroadcastManager.sendBroadcast(intent);
  }

  @Setter private OnLoginResultListener onLoginResultListener;

  interface OnLoginResultListener {
    void onLoginSuccess();

    void onLoginFailure(String errorMsg);

    void onLoginRequired(String errorMsg, int remaningAttempts);

    void onLoginFinished();
  }
}
