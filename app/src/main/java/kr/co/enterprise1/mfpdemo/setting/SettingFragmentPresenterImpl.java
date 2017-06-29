package kr.co.enterprise1.mfpdemo.setting;

import android.util.Log;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingFragmentPresenterImpl
    implements SettingFragmentPresenter, MFPPushNotificationListener {
  private static final String TAG = "SettingFragmentPresenterImpl";
  private SettingFragmentPresenter.View view;
  private SettingModel model;

  SettingFragmentPresenterImpl(SettingFragmentPresenter.View view) {
    this.view = view;
    model = new SettingModel();
  }

  @Override public void onCreatePreferences() {
    view.addListener();
    view.showLoading();
    MFPPush.getInstance().getTags(new MFPPushResponseListener<List<String>>() {
      @Override public void onSuccess(List<String> strings) {
        model.setTagNames(strings.toArray(new String[strings.size()]));
        if (model.getTagNames().length > 0) {
          view.setTagEntries(model.getTagNames());
        } else {
          view.disableTags();
        }
        view.hideLoading();
      }

      @Override public void onFailure(MFPPushException e) {
        view.hideLoading();
      }
    });
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

  @Override public void onTagsNotificationsPreferencChange(Object newValue) {
    view.showLoading();
    HashSet<String> hashSet = (HashSet<String>) newValue;
    if (hashSet.size() > 0) {
      String[] tagNames = hashSet.toArray(new String[hashSet.size()]);
      MFPPush.getInstance().subscribe(tagNames, new MFPPushResponseListener<String[]>() {
        @Override public void onSuccess(String[] strings) {
          view.hideLoading();
        }

        @Override public void onFailure(MFPPushException e) {
          view.hideLoading();
        }
      });
    } else {
      MFPPush.getInstance()
          .unsubscribe(model.getTagNames(), new MFPPushResponseListener<String[]>() {
            @Override public void onSuccess(String[] strings) {
              view.hideLoading();
            }

            @Override public void onFailure(MFPPushException e) {
              view.hideLoading();
            }
          });
    }
  }

  private void unRegisterDevice() {
    MFPPush.getInstance().unregisterDevice(new MFPPushResponseListener<String>() {
      @Override public void onSuccess(String s) {
        Log.d(TAG, "response = " + s);
        view.showOffSwitch();
        view.hideLoading();
        view.disableTags();
      }

      @Override public void onFailure(MFPPushException e) {
        e.printStackTrace();
        view.showOnSwitch();
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
        view.enableTags();
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
    Log.d(TAG,
        "alert = " + alert + " \n" + "ID = " + alertID + "\n" + "alertPayload = " + alertPayload);
    // Show the received notification in an AlertDialog
    view.showNotificationsAlert("Push Notifications", mfpSimplePushNotification.getAlert());
  }
}
