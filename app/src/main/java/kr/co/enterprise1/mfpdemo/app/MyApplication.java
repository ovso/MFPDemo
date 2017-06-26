package kr.co.enterprise1.mfpdemo.app;

import android.app.Application;
import android.content.ContextWrapper;
import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;
import com.worklight.wlclient.api.WLClient;
import kr.co.enterprise1.mfpdemo.common.Constants;

public class MyApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
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
  }

  private void initPreferences() {
    new Prefs.Builder().setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(Constants.PREFERENCES_FILE)
        .build();
  }
}
