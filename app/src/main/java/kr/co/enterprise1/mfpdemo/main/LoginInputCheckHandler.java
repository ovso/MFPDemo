package kr.co.enterprise1.mfpdemo.main;

import android.content.Context;
import kr.co.enterprise1.mfpdemo.R;
import lombok.Setter;

class LoginInputCheckHandler {
  private Context context;

  LoginInputCheckHandler(Context context) {
    this.context = context;
  }

  public void check(String id, String pw, boolean isRemember) {
    boolean isIdEmpty = id.isEmpty();
    boolean isPwEmpty = pw.isEmpty();
    if (isIdEmpty) {
      onInputResultListener.idError(context.getString(R.string.erroe_require_id));
    } else {
      onInputResultListener.idError("");
    }
    if (isPwEmpty) {
      onInputResultListener.pwError(context.getString(R.string.error_require_pw));
    } else {
      onInputResultListener.pwError("");
    }
    if (!isIdEmpty && !isPwEmpty) {
      onInputResultListener.pass(id, pw, isRemember);
    }
  }

  @Setter private OnInputResultListener onInputResultListener;

  public interface OnInputResultListener {
    void idError(String msg);

    void pwError(String msg);

    void pass(String id, String pw, boolean isRemember);
  }
}
