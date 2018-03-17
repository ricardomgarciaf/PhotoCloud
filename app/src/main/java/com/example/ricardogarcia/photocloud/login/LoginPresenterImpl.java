package com.example.ricardogarcia.photocloud.login;

/**
 * Created by Ricardo Garcia on 3/17/2018.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView=loginView;
        this.loginInteractor=loginInteractor;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(loginView!=null) {
            loginView.showProgress();
        }
        loginInteractor.login(username,password,this);
    }

    @Override
    public void onDestroy() {
        loginView=null;
    }

    @Override
    public void onUsernameError() {
        if(loginView!=null) {
            loginView.hideProgress();
            loginView.setUsernameError();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginView!=null) {
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView!=null) {
            loginView.hideProgress();
            loginView.goToHome();
        }
    }

    @Override
    public void onInvalidCredentials() {
        if(loginView!=null) {
            loginView.hideProgress();
            loginView.showInvalidCredentials();
        }
    }

    @Override
    public void onFailure() {
        if(loginView!=null) {
            loginView.hideProgress();
            loginView.showInvalidCredentials();
        }
    }
}
