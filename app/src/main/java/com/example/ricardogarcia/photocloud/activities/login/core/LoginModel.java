package com.example.ricardogarcia.photocloud.activities.login.core;

import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class LoginModel {

    private LoginActivity context;
    private PhotoCloudApiInterface api;

    public LoginModel(LoginActivity context, PhotoCloudApiInterface api) {
        this.context = context;
        this.api = api;
    }

    Disposable login(String username, String password, final OnLoginFinishedListener listener){
        if(username.isEmpty() || (!username.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches())){
            listener.onUsernameError();
            return null;
        }
        if(password.isEmpty()){
            listener.onPasswordError();
            return null;
        }
        return api.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(serviceResponse -> {
                    Timber.d("ServiceResponse "+serviceResponse.getCode());
                    switch (serviceResponse.getCode()) {
                        case 0:
                            listener.onInvalidCredentials();
                            break;
                        case 1:
                            PhotoCloudApplication.editor.putString(PhotoCloudApplication.KEY_USER,username);
                            PhotoCloudApplication.editor.commit();
                            listener.onSuccess();
                            break;
                        case 2:
                            listener.onUserBlocked();
                            break;
                        default:
                            listener.onFailure();
                            break;
                    }
                }, throwable -> {
                    if(throwable!=null){
                        throwable.printStackTrace();
                    }
                    listener.onFailure();
                });
    }

    public void goToHome(){
        context.goHome();
    }
}
