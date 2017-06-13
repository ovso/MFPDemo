package kr.co.enterprise1.mfpdemo.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;

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
    mPresenter.onSignInClick(id, pw);
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_login;
  }

  @Override public void showIdError(@StringRes int resId) {
    mIdTextInputLayout.setError(resId == 0 ? "" : getString(resId));
  }

  @Override public void showPwError(@StringRes int resId) {
    mPwTextInputLayout.setError(resId == 0 ? "" : getString(resId));
  }

  @BindView(R.id.root_login) View mRootView;

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

  @Override protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }
}