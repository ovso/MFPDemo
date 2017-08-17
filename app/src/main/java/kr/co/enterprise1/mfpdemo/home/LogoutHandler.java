package kr.co.enterprise1.mfpdemo.home;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import com.squareup.otto.Subscribe;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.analytics.Analytics;
import kr.co.enterprise1.mfpdemo.eventbus.BusProvider;
import kr.co.enterprise1.mfpdemo.eventbus.LogoutEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LogoutSuccessEvent;
import lombok.Setter;

class LogoutHandler {
  private LocalBroadcastManager localBroadcastManager;
  private Context context;

  LogoutHandler(Context context) {
    this.context = context;
    localBroadcastManager = LocalBroadcastManager.getInstance(context);
  }

  void logout() {
    BusProvider.getInstance().post(new LogoutEvent());
  }

  @Subscribe public void onLogoutSuccessEvent(LogoutSuccessEvent event) {
    onLogoutListener.onLogoutSuccess();
    Analytics.getInstance().logout();
  }

  void registerReceiver() {
    BusProvider.getInstance().register(this);
  }

  void unregisterReceiver() {
    BusProvider.getInstance().unregister(this);
  }

  String getAlertMessage() {
    return context.getString(R.string.logout_message);
  }

  @Setter private OnLogoutListener onLogoutListener;

  interface OnLogoutListener {
    void onLogoutSuccess();
  }
}