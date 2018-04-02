package com.example.ricardogarcia.photocloud.activities.login.core;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public interface OnLoginFinishedListener {
    void onUsernameError();

    void onPasswordError();

    void onSuccess();

    void onInvalidCredentials();

    void onUserBlocked();

    void onFailure();
}
