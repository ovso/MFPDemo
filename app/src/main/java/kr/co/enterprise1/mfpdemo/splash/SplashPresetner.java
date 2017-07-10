package kr.co.enterprise1.mfpdemo.splash;

/**
 * Created by jaeho on 2017. 7. 10
 */

public interface SplashPresetner {
  void onCreate();

  void onResume();

  void onPause();

  interface View {

    void navigateToLogin();

    void hideLogin();

    void showLogin();
  }
}
