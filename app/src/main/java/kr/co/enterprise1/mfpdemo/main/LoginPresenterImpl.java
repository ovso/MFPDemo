package kr.co.enterprise1.mfpdemo.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.otto.Subscribe;
import com.worklight.wlclient.api.WLClient;
import hugo.weaving.DebugLog;
import kr.co.enterprise1.mfpdemo.analytics.Analytics;
import kr.co.enterprise1.mfpdemo.common.Constants;
import kr.co.enterprise1.mfpdemo.eventbus.BusProvider;
import kr.co.enterprise1.mfpdemo.eventbus.LoginEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginFailureEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginRequiredEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginSuccessEvent;
import org.json.JSONException;
import org.json.JSONObject;

class LoginPresenterImpl implements LoginPresenter, MFPPushNotificationListener {
  private static final String TAG = "LoginPresenterImpl";
  private LoginPresenter.View view;
  private LoginInputCheckHandler loginInputCheckHandler;

  LoginPresenterImpl(LoginPresenter.View view) {
    this.view = view;
    Context context = WLClient.getInstance().getContext();
    loginInputCheckHandler = new LoginInputCheckHandler(context);
    loginInputCheckHandler.setOnInputResultListener(onInputResultListener());
  }

  @Override public void onCreate() {
    Analytics.getInstance().addDeviceEventListener();
  }

  @Override public void onUpdateClick() {
    view.exitApp();
  }

  @Override public void onDestroy() {
    Analytics.getInstance().removeDeviceEventListener();
    Analytics.getInstance().send();
  }

  private LoginInputCheckHandler.OnInputResultListener onInputResultListener() {
    return new LoginInputCheckHandler.OnInputResultListener() {
      @Override public void idError(String msg) {
        view.showIdError(msg);
      }

      @Override public void pwError(String msg) {
        view.showPwError(msg);
      }

      @DebugLog @Override public void pass(String id, String pw, boolean isRemember) {
        view.showLoading();
        BusProvider.getInstance().post(new LoginEvent(id, pw, isRemember));
        Analytics.getInstance().log("LoginScreen", "login", "login");
      }
    };
  }

  @Override public void onLoginClick(String id, String pw, boolean isRemember) {
    loginInputCheckHandler.check(id, pw, isRemember);
  }

  @Override public void onStart() {
    // Do something...
  }

  @Override public void onPause() {
    MFPPush.getInstance().hold();
    BusProvider.getInstance().unregister(this);
  }

  @Override public void onResume() {
    MFPPush.getInstance().listen(this);
    BusProvider.getInstance().register(this);
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

  @Subscribe public void onLoginRequiredEvent(LoginRequiredEvent event) {
    view.showErrorSnackbar(event.getErrorMsg(), event.getRemainingAttempts());
    view.hideLoading();
  }

  @Subscribe public void onLoginSuccessEvent(LoginSuccessEvent event) {
    String userInfo = Prefs.getString(Constants.PREFERENCES_KEY_USER, null);
    if (!TextUtils.isEmpty(userInfo)) {
      try {
        JSONObject userJson = new JSONObject(userInfo);
        String id = userJson.getString("id");
        Analytics.getInstance().login(id);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    view.navigateToHome();
    view.hideLoading();
  }

  @Subscribe public void onLoginFailureEvent(LoginFailureEvent event) {
    view.showErrorSnackbar(event.getErrorMsg());
    view.hideLoading();
  }

  void login(String id, String pw, boolean isRemember) {
    BusProvider.getInstance().post(new LoginEvent(id, pw, isRemember));
  }
}