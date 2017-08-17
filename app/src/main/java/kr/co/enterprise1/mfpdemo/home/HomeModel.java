package kr.co.enterprise1.mfpdemo.home;

import android.content.Context;
import com.pixplicity.easyprefs.library.Prefs;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

class HomeModel {
  private String user;

  private Context context;

  public HomeModel(Context context) {
    this.context = context;
  }

  public String getDisplayName() {
    try {
      String temp = Prefs.getString(Constants.PREFERENCES_KEY_USER, "?");
      JSONObject user = new JSONObject(temp);
      return context.getString(R.string.hello_user) + user.getString("displayName");
    } catch (JSONException e) {
      e.printStackTrace();
      return context.getString(R.string.hello_user) + "?";
    }
  }
}
