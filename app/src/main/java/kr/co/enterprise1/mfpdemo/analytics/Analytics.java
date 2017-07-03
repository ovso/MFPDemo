package kr.co.enterprise1.mfpdemo.analytics;

import android.app.Application;
import android.content.Context;
import com.worklight.common.WLAnalytics;

/**
 * Created by jaeho on 2017. 7. 3
 */

public class Analytics {
  public final static void init(Application application) {
    WLAnalytics.init(application);
  }

}
