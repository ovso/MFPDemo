package kr.co.enterprise1.mfpdemo.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.Constants;
import lombok.Setter;

class LogoutHandler {
  private LocalBroadcastManager localBroadcastManager;
  private Context context;

  LogoutHandler(Context context) {
    this.context = context;
    localBroadcastManager = LocalBroadcastManager.getInstance(context);
  }

  void logout() {
    Intent intent = new Intent(Constants.ACTION_LOGOUT);
    localBroadcastManager.sendBroadcast(intent);
  }

  private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      onLogoutListener.onLogoutSuccess();
    }
  };

  void registerReceiver() {
    localBroadcastManager.registerReceiver(logoutReceiver,
        new IntentFilter(Constants.ACTION_LOGOUT_SUCCESS));
  }

  void unregisterReceiver() {
    localBroadcastManager.unregisterReceiver(logoutReceiver);
  }

  String getAlertMessage() {
    return context.getString(R.string.logout_message);
  }

  @Setter private OnLogoutListener onLogoutListener;

  interface OnLogoutListener {
    void onLogoutSuccess();
  }
}