package kr.co.enterprise1.mfpdemo.main;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;
import java.net.URI;
import java.net.URISyntaxException;
import kr.co.enterprise1.mfpdemo.main.vo.VersionCheck;
import lombok.Setter;

public class VersionCheckInteractor {
  private final static String TAG = "VersionCheckInteractor";

  public void check() {
    URI adapterPath;
    try {
      adapterPath = new URI("/adapters/VersionCheck/version");
    } catch (URISyntaxException e) {
      e.printStackTrace();
      adapterPath = null;
    }
    WLResourceRequest request = new WLResourceRequest(adapterPath, WLResourceRequest.GET);
    request.send(new WLResponseListener() {
      @Override public void onSuccess(WLResponse wlResponse) {
        String jsonString = wlResponse.getResponseText();
        onVersionCheckListener.onSuccess(new Gson().fromJson(jsonString, VersionCheck.class));
      }

      @Override public void onFailure(WLFailResponse wlFailResponse) {
        Log.d(TAG, "onFailure() responseText = " + wlFailResponse.getResponseText());
      }
    });
  }

  @NonNull @Setter private OnVersionCheckListener onVersionCheckListener;

  interface OnVersionCheckListener {
    void onSuccess(VersionCheck version);
  }
}