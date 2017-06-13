package kr.co.enterprise1.mfpdemo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import kr.co.enterprise1.mfpdemo.R;
import kr.co.enterprise1.mfpdemo.common.AbsBaseActivity;

public class HomeActivity extends AbsBaseActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_home;
  }
}