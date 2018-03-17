package com.example.ricardogarcia.photocloud.login;

/**
 * Created by Ricardo Garcia on 3/17/2018.
 */

public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();
}
