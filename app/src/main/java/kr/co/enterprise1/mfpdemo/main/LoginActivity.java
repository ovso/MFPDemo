package kr.co.enterprise1.mfpdemo.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;
import kr.co.enterprise1.mfpdemo.home.HomeActivity;

public class LoginActivity extends AbsBaseActivity implements LoginPresenter.View {
  @BindView(R.id.id_edittext) TextInputEditText mIdEditText;
  @BindView(R.id.pw_edittext) TextInputEditText mPwEditText;
  @BindView(R.id.id_textinputlayout) TextInputLayout mIdTextInputLayout;
  @BindView(R.id.pw_textinputlayout) TextInputLayout mPwTextInputLayout;

  private LoginPresenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new LoginPresenterImpl(this);
  }

  @OnClick(R.id.sign_in_button) void onSignInClick() {
    String id = mIdEditText.getText().toString().trim();
    String pw = mPwEditText.getText().toString().trim();
    mPresenter.onLoginClick(id, pw);
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

  @Override public void showErrorAlert(String title, String errorMsg) {
    new AlertDialog.Builder(this).setTitle(title)
        .setMessage(errorMsg)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void navigateToHome() {
    Intent intent = new Intent(this, HomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
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