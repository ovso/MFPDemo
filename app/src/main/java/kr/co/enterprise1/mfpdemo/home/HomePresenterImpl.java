package kr.co.enterprise1.mfpdemo.home;

import kr.co.enterprise1.mfpdemo.app.MyApplication;

class HomePresenterImpl implements HomePresenter {

  private HomePresenter.View view;
  private LogoutHandler logoutHandler;
  private HomeModel model;

  HomePresenterImpl(HomePresenter.View view) {
    this.view = view;
    model = new HomeModel(MyApplication.getContext());
    logoutHandler = new LogoutHandler(MyApplication.getContext());
    logoutHandler.setOnLogoutListener(() -> {
      view.navigateToLogin();
    });
  }

  @Override public void onCreate() {
    view.showHello(model.getDisplayName());
  }

  @Override public void onGetBanlanceClick() {

  }

  @Override public void onLogoutClick() {
    logoutHandler.logout();
  }

  @Override public void onStart() {
    logoutHandler.registerReceiver();
  }

  @Override public void onPause() {
    logoutHandler.unregisterReceiver();
  }

  @Override public void onBackPressed() {
    view.showLogoutAlert("!", logoutHandler.getAlertMessage());
  }

  @Override public void onLogoutDialogOkClick() {
    logoutHandler.logout();
  }
}