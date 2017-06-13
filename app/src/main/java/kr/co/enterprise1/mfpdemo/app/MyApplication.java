package kr.co.enterprise1.mfpdemo.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import com.pixplicity.easyprefs.library.Prefs;
import com.worklight.wlclient.api.WLClient;
import kr.co.enterprise1.mfpdemo.common.Constants;
import lombok.Getter;

public class MyApplication extends Application {
  @Getter private static Context context;

  @Override public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    WLClient.createInstance(this);
    UserLoginChallengeHandler.createAndRegister();
    new Prefs.Builder().setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(Constants.PREFERENCES_FILE)
        .setUseDefaultSharedPreference(true)
        .build();
  }
}
