package kr.co.enterprise1.mfpdemo.setting;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 6. 29
 */

class SettingModel {
  @Setter @Getter private String[] tagNames;

  public static String getAppVersion(Context context) {
    // application version
    String versionName = "";
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionName = info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      Log.d("SettingModel", e.getMessage(), e);
    }

    return versionName;
  }
}
