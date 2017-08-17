package kr.co.enterprise1.mfpdemo.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by jaeho on 2017. 7. 6
 */

public class BusProvider extends Bus {
  private static final BusProvider ourInstance = new BusProvider();
  public static BusProvider getInstance() {
    return ourInstance;
  }

  private BusProvider() {
  }
}
