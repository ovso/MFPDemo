package kr.co.enterprise1.mfpdemo.home;

import android.content.Context;
import android.util.Log;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.worklight.wlclient.api.WLClient;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.analytics.Analytics;

class HomePresenterImpl implements HomePresenter, MFPPushNotificationListener {

  private static final String TAG = "HomePresenterImpl";
  private HomePresenter.View view;
  private LogoutHandler logoutHandler;
  private HomeModel model;

  HomePresenterImpl(HomePresenter.View view) {
    this.view = view;
    Context context = WLClient.getInstance().getContext();
    model = new HomeModel(context);
    logoutHandler = new LogoutHandler(context);
    logoutHandler.setOnLogoutListener(() -> view.navigateToLogin());
  }

  @Override public void onCreate() {
    view.showHello(model.getDisplayName());
  }

  @Override public void onLogoutClick() {
    view.showLogoutAlert("!", logoutHandler.getAlertMessage());//logoutHandler.logout();
  }

  @Override public void onStart() {
    MFPPush.getInstance().listen(this);
    logoutHandler.registerReceiver();
  }

  @Override public void onPause() {
    logoutHandler.unregisterReceiver();
    MFPPush.getInstance().hold();
  }

  @Override public void onBackPressed() {
    //view.showLogoutAlert("!", logoutHandler.getAlertMessage());
    view.activityFinish();
  }

  @Override public void onLogoutDialogOkClick() {
    logoutHandler.logout();
  }

  @Override public void onDestroy() {
    Analytics.getInstance().send();
  }

  @Override public void onSettingClick() {
    view.navigateToSetting();
  }

  @Override public void onCrashClick(int id) {
    switch (id) {
      case R.id.crash_1_button:
        throw new ArrayIndexOutOfBoundsException("Crash button 1");
      case R.id.crash_2_button:
        throw new NumberFormatException("Crash button 2");
      case R.id.crash_3_button:
        throw new ClassCastException("Crash button 3");
      case R.id.crash_4_button:
        throw new RuntimeException("Crash button 4");

    }
  }

  @Override public void onReceive(MFPSimplePushNotification mfpSimplePushNotification) {
    Log.i("Push Notifications", mfpSimplePushNotification.getAlert());

    String alert = "Alert: " + mfpSimplePushNotification.getAlert();
    String alertID = "ID: " + mfpSimplePushNotification.getId();
    String alertPayload = "Payload: " + mfpSimplePushNotification.getPayload();
    Log.d(TAG,
        "alert = " + alert + " \n" + "ID = " + alertID + "\n" + "alertPayload = " + alertPayload);
    // Show the received notification in an AlertDialog
    view.showNotificationsAlert("Push Notifications", mfpSimplePushNotification.getAlert());

  }
}