package kr.co.enterprise1.mfpdemo.eventbus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jaeho on 2017. 7. 6
 */

@AllArgsConstructor public class LoginEvent {
  @Getter private String action;
}
