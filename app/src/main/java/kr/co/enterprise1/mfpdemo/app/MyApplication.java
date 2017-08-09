package kr.co.enterprise1.mfpdemo.app;

import android.app.Application;
import android.content.ContextWrapper;
import com.facebook.stetho.Stetho;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.pixplicity.easyprefs.library.Prefs;
import com.worklight.wlclient.api.WLClient;
import kr.co.enterprise1.mfpdemo.analytics.Analytics;
import kr.co.enterprise1.mfpdemo.common.Constants;
import lombok.Getter;

public class MyApplication extends Application {
  @Getter private static MyApplication instance;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initWLClient();
    initPreferences();
    initUserLoginChallengeHandler();
    initStetho();
  }

  private void initStetho() {
    Stetho.initializeWithDefaults(this);
  }

  private void initUserLoginChallengeHandler() {
    UserLoginChallengeHandler.createAndRegister();
  }

  private void initWLClient() {
    WLClient.createInstance(this);
    MFPPush.getInstance().initialize(this);
    Analytics.getInstance().initialize(this);
    //WLAnalytics.init(this);

  }

  private void initPreferences() {
    new Prefs.Builder().setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(Constants.PREFERENCES_FILE)
        .build();
  }
}
