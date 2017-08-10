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
import kr.co.enterprise1.mfpdemo.eventbus.LoginSuccessEvent;

/**
 * Created by jaeho on 2017. 7. 10
 */

class SplashPresenterImpl implements SplashPresetner {
  private static final String TAG = "SplashPresenterImpl";
  private SplashPresetner.View view;
  private Handler handler = new Handler();

  SplashPresenterImpl(SplashPresetner.View view) {
    this.view = view;
  }

  private Runnable run = () -> obtainAccessToken();

  @Override public void onCreate() {
    view.showLogin();
    handler.postDelayed(run, 2000);
  }
  private boolean isError;
  @DebugLog private void obtainAccessToken() {
    WLAuthorizationManager.getInstance()
        .obtainAccessToken("UserLogin", new WLAccessTokenListener() {
          @DebugLog @Override public void onSuccess(AccessToken accessToken) {
            Log.d(TAG, "scope = " + accessToken.getScope());
            Log.d(TAG, "value = " + accessToken.getValue());
            Log.d(TAG, "AuthRequstHeader = " + accessToken.getAsAuthorizationRequestHeader());
            Log.d(TAG, "parameter = " + accessToken.getAsFormEncodedBodyParameter());
            isError = false;
          }

          @DebugLog @Override public void onFailure(WLFailResponse wlFailResponse) {
            Log.d(TAG, "errorMsg = " + wlFailResponse.getErrorMsg());
            Log.d(TAG, "errorStatusCode = " + wlFailResponse.getErrorStatusCode());
            handler.post(() -> view.showToast(wlFailResponse.getErrorMsg()));
            isError = true;
          }
        });
  }

  @DebugLog @Subscribe public void onLoginRequiredEvent(LoginRequiredEvent event) {
    view.navigateToLogin();
    view.hideLogin();
    view.activityFinish();
  }

  @DebugLog @Subscribe public void onLoginSuccessEvent(LoginSuccessEvent event) {
    view.navigateToHome();
    view.hideLogin();
    view.activityFinish();
  }

  @Override public void onResume() {
    BusProvider.getInstance().register(this);
    if(isError) {
      view.activityFinish();
    }
  }

  @Override public void onPause() {
    BusProvider.getInstance().unregister(this);
  }

  @Override public void onBackPressed() {
    handler.removeCallbacks(run);
    view.activityFinish();
  }
}