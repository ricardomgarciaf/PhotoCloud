package com.example.ricardogarcia.photocloud.activities.login.core;

import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;

import io.reactivex.disposables.Disposable;


/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class LoginPresenter implements OnLoginFinishedListener{

    private Disposable disposable;
    private LoginActivity view;
    private LoginModel model;

    public LoginPresenter(LoginActivity view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void onDestroy(){
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public void validateCredentials(String username, String password){
        if(view!=null){
            view.showProgress();
        }
        disposable=model.login(username,password,this);
    }

    @Override
    public void onUsernameError() {
        if(view!=null) {
            view.hideProgress();
            view.setUsernameError();
        }
    }

    @Override
    public void onPasswordError() {
        if(view!=null) {
            view.hideProgress();
            view.setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        model.goToHome();
    }

    @Override
    public void onInvalidCredentials() {
        view.hideProgress();
        view.showInvalidCredentials();
    }

    @Override
    public void onUserBlocked() {
        view.hideProgress();
        view.showUserBlocked();
    }

    @Override
    public void onFailure() {
        view.hideProgress();
        view.showError();
    }
}
