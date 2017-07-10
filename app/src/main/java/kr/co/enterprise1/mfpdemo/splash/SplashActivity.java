package kr.co.enterprise1.mfpdemo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.github.ybq.android.spinkit.SpinKitView;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;
import kr.co.enterprise1.mfpdemo.home.HomeActivity;
import kr.co.enterprise1.mfpdemo.main.LoginActivity;

/**
 * Created by jaeho on 2017. 7. 10
 */

public class SplashActivity extends AbsBaseActivity implements SplashPresetner.View {
  private static final String TAG = "SplashActivity";
  @BindView(R.id.loadingbar) SpinKitView mLoadingbar;
  private SplashPresetner mPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new SplashPresenterImpl(this);
    mPresenter.onCreate();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_splash;
  }

  @Override protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override public void onBackPressed() {
    mPresenter.onBackPressed();
  }

  @Override public void navigateToLogin() {
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
  }

  @Override public void hideLogin() {
    mLoadingbar.setVisibility(View.INVISIBLE);
  }

  @Override public void showLogin() {
    mLoadingbar.setVisibility(View.VISIBLE);
  }

  @Override public void navigateToHome() {
    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    startActivity(intent);
  }

  @Override public void activityFinish() {
    //super.onBackPressed();
    finishAffinity();
  }
}