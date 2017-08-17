package kr.co.enterprise1.mfpdemo.analytics;

import android.app.Application;
import android.util.Log;
import com.worklight.common.WLAnalytics;
import com.worklight.wlclient.WLRequestListener;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaeho on 2017. 7. 4
 */

public class Analytics {
  private static final Analytics ourInstance = new Analytics();

  public static Analytics getInstance() {
    return ourInstance;
  }

  /**
   * Initialized from Applcation.class
   */
  public void initialize(Application application) {
    WLAnalytics.init(application);
  }

  public void addDeviceEventListener() {
    WLAnalytics.addDeviceEventListener(WLAnalytics.DeviceEvent.NETWORK);
    WLAnalytics.addDeviceEventListener(WLAnalytics.DeviceEvent.LIFECYCLE);
  }

  public void removeDeviceEventListener() {
    WLAnalytics.removeDeviceEventListener(WLAnalytics.DeviceEvent.NETWORK);
    WLAnalytics.removeDeviceEventListener(WLAnalytics.DeviceEvent.LIFECYCLE);
  }

  public void log(String tag, String key, String value) {
    JSONObject json = new JSONObject();
    try {
      json.put(key, value);
      // do something..
    } catch (JSONException e) {
      e.printStackTrace();
    }

    WLAnalytics.log(tag, json);
  }
  public void send() {
    WLAnalytics.send();;
  }
  public void login(String userId) {
    WLAnalytics.setUserContext(userId);
  }
  public void logout() {
    WLAnalytics.unsetUserContext();
  }

  private Analytics() {

  }
}
