package kr.co.enterprise1.mfpdemo.setting;

import android.util.Log;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingFragmentPresenterImpl
    implements SettingFragmentPresenter, MFPPushNotificationListener {
  private static final String TAG = "SettingFragmentPresenterImpl";
  private SettingFragmentPresenter.View view;

  SettingFragmentPresenterImpl(SettingFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreatePreferences() {
    view.addListener();
  }

  @Override public void onNotificationsPreferenceChange(boolean value) {
    view.showLoading();
    if (value) {
      if (MFPPush.getInstance().isPushSupported()) {
        registerDevice();
      }
    } else {
      unRegisterDevice();
    }
  }

  @Override public void onPause() {
    MFPPush.getInstance().hold();
  }

  @Override public void onResume() {
    MFPPush.getInstance().listen(this);
  }

  private void unRegisterDevice() {
    MFPPush.getInstance().unregisterDevice(new MFPPushResponseListener<String>() {
      @Override public void onSuccess(String s) {
        Log.d(TAG, "response = " + s);
        view.showOffSwitch();
        view.hideLoading();
      }

      @Override public void onFailure(MFPPushException e) {
        e.printStackTrace();
        view.showOffSwitch();
        view.hideLoading();
      }
    });
  }

  private void registerDevice() {
    MFPPush.getInstance().registerDevice(null, new MFPPushResponseListener<String>() {
      @Override public void onSuccess(String s) {
        Log.d(TAG, "response = " + s);
        view.showOnSwitch();
        view.hideLoading();
      }

      @Override public void onFailure(MFPPushException e) {
        e.printStackTrace();
        view.showOffSwitch();
        view.hideLoading();
      }
    });
  }

  @Override public void onReceive(MFPSimplePushNotification mfpSimplePushNotification) {
    Log.i("Push Notifications", mfpSimplePushNotification.getAlert());

    String alert = "Alert: " + mfpSimplePushNotification.getAlert();
    String alertID = "ID: " + mfpSimplePushNotification.getId();
    String alertPayload = "Payload: " + mfpSimplePushNotification.getPayload();

    // Show the received notification in an AlertDialog
    view.showNotificationsAlert("Push Notifications", alert + "\n" + alertID + "\n" + alertPayload);
  }
}
