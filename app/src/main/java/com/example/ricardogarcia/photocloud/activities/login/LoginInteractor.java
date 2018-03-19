package com.example.ricardogarcia.photocloud.activities.login;

/**
 * Created by Ricardo Garcia on 3/17/2018.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener{

        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onInvalidCredentials();

        void onFailure();
    }

    void login(String username, String password, OnLoginFinishedListener listener);
}
