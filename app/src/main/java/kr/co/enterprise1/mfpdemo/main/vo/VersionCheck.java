package kr.co.enterprise1.mfpdemo.main.vo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 6. 19
 */
@ToString @Data public class VersionCheck {
  private String release_date;
  private String mobile_version;
  private String center_version;
  private boolean product;
  private boolean force_update;
  private String title;
  private String message;
}
