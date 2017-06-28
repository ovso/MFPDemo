package kr.co.enterprise1.mfpdemo.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import kr.co.enterprise1.mfpdemo.R;

/**
 * Created by jaeho on 2017. 6. 28
 */

public class SettingActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.container, new SettingFragment())
        .commit();
  }
}
