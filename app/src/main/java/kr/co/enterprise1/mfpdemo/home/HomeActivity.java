package kr.co.enterprise1.mfpdemo.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;
import kr.co.enterprise1.mfpdemo.main.LoginActivity;

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

  @OnClick(R.id.getbalance_button) void onGetBanlanceClick() {
    mPresenter.onGetBanlanceClick();
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
}