package kr.co.enterprise1.mfpdemo.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;
import kr.co.enterprise1.mfpdemo.home.HomeActivity;
import kr.co.enterprise1.mfpdemo.main.vo.VersionCheck;

public class LoginActivity extends AbsBaseActivity implements LoginPresenter.View {
  @BindView(R.id.id_edittext) TextInputEditText mIdEditText;
  @BindView(R.id.pw_edittext) TextInputEditText mPwEditText;
  @BindView(R.id.id_textinputlayout) TextInputLayout mIdTextInputLayout;
  @BindView(R.id.pw_textinputlayout) TextInputLayout mPwTextInputLayout;
  @BindView(R.id.remember_checkbox) CheckBox mRememberCheckBox;

  private LoginPresenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new LoginPresenterImpl(this);
    mPresenter.onCreate();
  }

  @OnClick(R.id.sign_in_button) void onSignInClick() {
    String id = mIdEditText.getText().toString().trim();
    String pw = mPwEditText.getText().toString().trim();
    mPresenter.onLoginClick(id, pw, mRememberCheckBox.isChecked());
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_login;
  }

  @Override public void showIdError(String msg) {
    mIdTextInputLayout.setError(msg);
  }

  @Override public void showPwError(String msg) {
    mPwTextInputLayout.setError(msg);
  }

  @BindView(R.id.login_container) View mRootView;

  @Override public void showErrorSnackbar(String errorMsg, int remainingAttempts) {
    Snackbar.make(mRootView, errorMsg + "\n" + "남은 시도 횟수 : " + remainingAttempts,
        Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showErrorSnackbar(String errorMsg) {
    Snackbar.make(mRootView, errorMsg, Snackbar.LENGTH_LONG).show();
  }

  @Override public void showErrorAlert(String title, String errorMsg) {
    new AlertDialog.Builder(this).setTitle(title)
        .setMessage(errorMsg)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void navigateToHome() {
    if (isTaskRoot()) {
      Intent intent = new Intent(this, HomeActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      startActivity(intent);
    }
  }

  @BindView(R.id.loading_progressbar) ProgressBar mLoadingProgressBar;

  @Override public void showLoading() {
    mLoadingProgressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    mLoadingProgressBar.setVisibility(View.GONE);
  }

  @Override public void showUpdateAlert(VersionCheck version) {
    Runnable runnable =
        () -> new AlertDialog.Builder(LoginActivity.this).setTitle(version.getTitle())
            .setMessage(version.getMessage())
            .setCancelable(!version.isForce_update())
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
              if (version.isForce_update()) {
                LoginActivity.this.finish();
              }
            })
            .setPositiveButton(android.R.string.ok, (dialog, which) -> mPresenter.onUpdateClick())
            .show();
    runOnUiThread(runnable);
  }

  @Override public void exitApp() {
    finish();
  }

  @Override public void showNotificationsAlert(String title, String message) {
    new AlertDialog.Builder(LoginActivity.this).setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show();
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

  @Override protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override public void onBackPressed() {
    ActivityCompat.finishAffinity(this);
    System.runFinalization();
    System.exit(0);
  }
}