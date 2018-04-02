package com.example.ricardogarcia.photocloud.activities.login.core;

import com.example.ricardogarcia.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class LoginModel {
    LoginActivity context;
    PhotoCloudApiInterface api;

    public LoginModel(LoginActivity context, PhotoCloudApiInterface api) {
        this.context = context;
        this.api = api;
    }

    Disposable login(String username, String password, final OnLoginFinishedListener listener){
        if(username.isEmpty()){
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
                .subscribe(new Consumer<ServiceResponse>() {
                    @Override
                    public void accept(ServiceResponse serviceResponse) throws Exception {
                        switch (serviceResponse.getCode()){
                            case 0:
                                listener.onInvalidCredentials();
                                break;
                            case 1:
                                listener.onSuccess();
                                break;
                            case 2:
                                listener.onUserBlocked();
                                break;
                            default:
                                listener.onFailure();
                                break;
                        }
                    }
                });
    }

    public void goToHome(){
        context.goHome();
    }
}
