package com.example.ricardogarcia.photocloud.login;

import android.text.TextUtils;

/**
 * Created by Ricardo Garcia on 3/17/2018.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {
        if(TextUtils.isEmpty(username)){
            listener.onUsernameError();
            return;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }

    }
}
