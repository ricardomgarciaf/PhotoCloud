package com.example.ricardogarcia.photocloud.activities.register.core;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */

public interface OnRegisterFinishedListener {
    void onFirstNameError();

    void onLastNameError();

    void onEmailError();

    void onPasswordError();

    void onAlreadyExistingEmail();

    void onSuccess();

    void onFailure();
}
