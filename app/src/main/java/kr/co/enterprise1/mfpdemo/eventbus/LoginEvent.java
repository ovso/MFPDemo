package kr.co.enterprise1.mfpdemo.eventbus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 7. 6
 */

@AllArgsConstructor public class LoginEvent {
  @Getter private String id;
  @Getter private String pw;
  @Getter private boolean remember;
}