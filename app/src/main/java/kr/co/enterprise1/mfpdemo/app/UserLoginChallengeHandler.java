package kr.co.enterprise1.mfpdemo.app;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.otto.Subscribe;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLLoginResponseListener;
import com.worklight.wlclient.api.WLLogoutResponseListener;
import com.worklight.wlclient.api.challengehandler.SecurityCheckChallengeHandler;
import hugo.weaving.DebugLog;
import kr.co.enterprise1.mfpdemo.common.Constants;
import kr.co.enterprise1.mfpdemo.eventbus.BusProvider;
import kr.co.enterprise1.mfpdemo.eventbus.LoginEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginFailureEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginRequiredEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LoginSuccessEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LogoutEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LogoutFailureEvent;
import kr.co.enterprise1.mfpdemo.eventbus.LogoutSuccessEvent;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright 2016 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class UserLoginChallengeHandler extends SecurityCheckChallengeHandler {
  private static String securityCheckName = "UserLogin";
  private int remainingAttempts = -1;
  private String errorMsg = "";
  //private Context context;
  private boolean isChallenged = false;
  private Handler handler;

  private UserLoginChallengeHandler() {
    super(securityCheckName);
    //Reset the current user
    Prefs.remove(Constants.PREFERENCES_KEY_USER);
    BusProvider.getInstance().register(this);
    handler = new Handler(Looper.getMainLooper());
  }

  @DebugLog @Subscribe public void onLogoutEvent(LogoutEvent event) {
    logout();
  }

  @DebugLog @Subscribe public void onLoginEvent(LoginEvent event) {
    try {
      JSONObject credentials = new JSONObject();
      credentials.put("username", event.getId());
      credentials.put("password", event.getPw());
      credentials.put("rememberMe", event.isRemember());
      new Thread(() -> login(credentials)).start();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  static UserLoginChallengeHandler createAndRegister() {
    UserLoginChallengeHandler challengeHandler = new UserLoginChallengeHandler();
    WLClient.getInstance().registerChallengeHandler(challengeHandler);
    return challengeHandler;
  }

  @DebugLog @Override public void handleChallenge(JSONObject jsonObject) {
    Log.d(securityCheckName, "Challenge Received");
    isChallenged = true;
    try {
      if (jsonObject.isNull("errorMsg")) {
        errorMsg = "";
      } else {
        errorMsg = jsonObject.getString("errorMsg");
      }

      remainingAttempts = jsonObject.getInt("remainingAttempts");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    handler.post(
        () -> BusProvider.getInstance().post(new LoginRequiredEvent(errorMsg, remainingAttempts)));
  }

  @DebugLog @Override public void handleFailure(JSONObject error) {
    super.handleFailure(error);
    isChallenged = false;
    if (error.isNull("failure")) {
      errorMsg = "Failed to login. Please try again later.";
    } else {
      try {
        errorMsg = error.getString("failure");
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    handler.post(() -> BusProvider.getInstance().post(new LoginFailureEvent(errorMsg)));
    Log.d(securityCheckName, "handleFailure");
  }

  @DebugLog @Override public void handleSuccess(JSONObject identity) {
    super.handleSuccess(identity);
    isChallenged = false;
    try {
      //Save the current user
      Prefs.putString(Constants.PREFERENCES_KEY_USER, identity.getJSONObject("user").toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    handler.post(() -> BusProvider.getInstance().post(new LoginSuccessEvent()));

    Log.d(securityCheckName, "handleSuccess");
  }

  @DebugLog private void login(JSONObject credentials) {
    if (isChallenged) {
      submitChallengeAnswer(credentials);
    } else {
      WLAuthorizationManager.getInstance()
          .login(securityCheckName, credentials, new WLLoginResponseListener() {
            @Override public void onSuccess() {
              Log.d(securityCheckName, "Login Preemptive Success");
            }

            @Override public void onFailure(WLFailResponse wlFailResponse) {
              Log.d(securityCheckName, "Login Preemptive Failure");
              String errorMsg = "UserLogin : Login Preemptive Failure";
              handler.post(() -> BusProvider.getInstance().post(new LoginFailureEvent(errorMsg)));
            }
          });
    }
  }

  @DebugLog private void logout() {
    WLAuthorizationManager.getInstance().logout(securityCheckName, new WLLogoutResponseListener() {
      @Override public void onSuccess() {
        Log.d(securityCheckName, "Logout Success");
        handler.post(() -> BusProvider.getInstance().post(new LogoutSuccessEvent()));
      }

      @Override public void onFailure(WLFailResponse wlFailResponse) {
        Log.d(securityCheckName, "Logout Failure");
        handler.post(() -> BusProvider.getInstance().post(new LogoutFailureEvent()));
      }
    });
  }
}
