package kr.co.enterprise1.mfpdemo.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;
import kr.co.enterprise1.mfpdemo.main.LoginActivity;
import kr.co.enterprise1.mfpdemo.setting.SettingActivity;

public class HomeActivity extends AbsBaseActivity implements HomePresenter.View {
  @BindView(R.id.hello_textview) TextView mHelloTextView;
  @BindView(R.id.result_textview) TextView mResultTextView;

  private HomePresenter mPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new HomePresenterImpl(this);
    mPresenter.onCreate();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_home;
  }

  @OnClick(R.id.setting_button) void onSettingClick() {
    mPresenter.onSettingClick();
  }

  @OnClick(R.id.logout_button) void onLogoutClick() {
    mPresenter.onLogoutClick();
  }

  @Override public void showHello(String text) {
    mHelloTextView.setText(text);
  }

  @Override public void navigateToLogin() {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override public void showLogoutAlert(String title, String message) {
    new AlertDialog.Builder(this).setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
          mPresenter.onLogoutDialogOkClick();
        })
        .setNegativeButton(android.R.string.cancel, null)
        .show();
  }

  @Override public void showNotificationsAlert(String title, String message) {
    new AlertDialog.Builder(HomeActivity.this).setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void activityFinish() {
    ActivityCompat.finishAffinity(this);
    System.runFinalization();
    System.exit(0);
  }

  @Override public void navigateToSetting() {
    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
    startActivity(intent);
  }

  @Override public void onBackPressed() {
    //super.onBackPressed();
    mPresenter.onBackPressed();
  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @OnClick({ R.id.crash_1_button, R.id.crash_2_button, R.id.crash_3_button, R.id.crash_4_button })
  void onCrashClick(View view) {
    mPresenter.onCrashClick(view.getId());
  }
}