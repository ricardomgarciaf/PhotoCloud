package com.example.ricardogarcia.photocloud.login;

/**
 * Created by Ricardo Garcia on 3/17/2018.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void showInvalidCredentials();

    void goToHome();
}
