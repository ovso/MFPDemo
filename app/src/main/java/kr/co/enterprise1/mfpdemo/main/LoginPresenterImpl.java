package kr.co.enterprise1.mfpdemo.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.pixplicity.easyprefs.library.Prefs;
import com.worklight.common.WLAnalytics;
import com.worklight.wlclient.api.WLClient;
import kr.co.enterprise1.mfpdemo.analytics.Analytics;
import kr.co.enterprise1.mfpdemo.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

class LoginPresenterImpl implements LoginPresenter, MFPPushNotificationListener {
  private static final String TAG = "LoginPresenterImpl";
  //private final static String TAG = "LoginPresenterImpl";
  private LoginPresenter.View view;
  private LoginInteractor loginInteractor;
  private CredentialsInputHandler credentialsInputHandler;
  private VersionCheckInteractor versionCheckInteractor;

  LoginPresenterImpl(LoginPresenter.View view) {
    this.view = view;
    Context context = WLClient.getInstance().getContext();
    loginInteractor = new LoginInteractor(context);
    loginInteractor.setOnLoginResultListener(onLoginResultListener());
    credentialsInputHandler = new CredentialsInputHandler(context);
    credentialsInputHandler.setOnInputResultListener(onInputResultListener());
    versionCheckInteractor = new VersionCheckInteractor();
    versionCheckInteractor.setOnVersionCheckListener(onVersionCheckListener());
  }

  @Override public void onCreate() {
    // versionCheckInteractor.check();
    Analytics.getInstance().addDeviceEventListener();
  }

  @Override public void onUpdateClick() {
    view.navigateToExternalAppCenter();
    view.exitApp();
  }

  @Override public void onDestroy() {
    Analytics.getInstance().removeDeviceEventListener();
    Analytics.getInstance().send();
  }

  private VersionCheckInteractor.OnVersionCheckListener onVersionCheckListener() {
    return version -> {
      if (version != null) {
        if (!version.getCenter_version().equals(version.getMobile_version())) {
          view.showUpdateAlert(version);
        }
      }
    };
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
        Analytics.getInstance().log("LoginScreen", "login", "login");

      }
    };
  }

  private LoginInteractor.OnLoginResultListener onLoginResultListener() {
    return new LoginInteractor.OnLoginResultListener() {
      @Override public void onLoginSuccess() {
        view.navigateToHome();
        //Prefs.putString(Constants.PREFERENCES_KEY_USER, identity.getJSONObject("user").toString());
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
      }

      @Override public void onLoginFailure(String errorMsg) {
        //view.showErrorAlert("!", errorMsg);
        view.showErrorSnackbar(errorMsg);
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
    MFPPush.getInstance().listen(this);
  }

  @Override public void onPause() {
    loginInteractor.unregisterReceiver();
    MFPPush.getInstance().hold();
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