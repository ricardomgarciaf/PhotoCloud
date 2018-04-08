package com.example.ricardogarcia.photocloud.activities.register.core;

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */

public interface RegisterView {

    void showProgress();

    void hideProgress();

    void setFirstNameError();

    void setLastNameError();

    void setEmailError();

    void setPasswordError();

    void showAlreadyExistingEmail();

    void showError();

    void goHome();
}
