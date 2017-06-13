package kr.co.enterprise1.mfpdemo.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbsBaseActivity extends AppCompatActivity {
  private Unbinder mUnbinder;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    mUnbinder = ButterKnife.bind(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mUnbinder.unbind();
  }

  @LayoutRes protected abstract int getLayoutResId();
}
