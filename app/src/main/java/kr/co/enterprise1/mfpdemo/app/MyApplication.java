package kr.co.enterprise1.mfpdemo.app;

import android.app.Application;
import android.content.Context;
import com.worklight.wlclient.api.WLClient;
import lombok.Getter;

public class MyApplication extends Application {
  @Getter private static Context context;

  @Override public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    WLClient.createInstance(this);
    UserLoginChallengeHandler.createAndRegister();
  }
}
