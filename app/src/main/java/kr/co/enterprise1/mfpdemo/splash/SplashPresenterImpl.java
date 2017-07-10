package kr.co.enterprise1.mfpdemo.splash;

import android.os.Handler;
import android.util.Log;
import com.squareup.otto.Subscribe;
import com.worklight.wlclient.api.WLAccessTokenListener;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.auth.AccessToken;
import hugo.weaving.DebugLog;
import kr.co.enterprise1.mfpdemo.eventbus.BusProvider;
import kr.co.enterprise1.mfpdemo.eventbus.LoginRequiredEvent;

/**
 * Created by jaeho on 2017. 7. 10
 */

class SplashPresenterImpl implements SplashPresetner {
  private static final String TAG = "SplashPresenterImpl";
  private SplashPresetner.View view;

  SplashPresenterImpl(SplashPresetner.View view) {
    this.view = view;
  }

  @Override public void onCreate() {
    view.showLogin();
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        obtainAccessToken();
      }
    }, 2000);
  }
  @DebugLog
  private void obtainAccessToken() {
    WLAuthorizationManager.getInstance()
        .obtainAccessToken("UserLogin", new WLAccessTokenListener() {
          @Override public void onSuccess(AccessToken accessToken) {
            Log.d(TAG, "scope = " + accessToken.getScope());
            Log.d(TAG, "value = " + accessToken.getValue());
            Log.d(TAG, "AuthRequstHeader = " + accessToken.getAsAuthorizationRequestHeader());
            Log.d(TAG, "parameter = " + accessToken.getAsFormEncodedBodyParameter());
          }

          @Override public void onFailure(WLFailResponse wlFailResponse) {
            Log.d(TAG, "errorMsg = " + wlFailResponse.getErrorMsg());
            Log.d(TAG, "errorStatusCode = " + wlFailResponse.getErrorStatusCode());
          }
        });
  }
  @DebugLog
  @Subscribe public void onLoginRequiredEvent(LoginRequiredEvent event) {
    view.hideLogin();
    view.navigateToLogin();
  }


  @Override public void onResume() {
    BusProvider.getInstance().register(this);
  }

  @Override public void onPause() {
    BusProvider.getInstance().unregister(this);
  }
}