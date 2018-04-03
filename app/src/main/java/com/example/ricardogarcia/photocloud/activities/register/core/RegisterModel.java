package com.example.ricardogarcia.photocloud.activities.register.core;

import com.example.ricardogarcia.model.ServiceResponse;
import com.example.ricardogarcia.model.User;
import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */

public class RegisterModel {
    RegisterActivity registerActivity;
    PhotoCloudApiInterface api;

    public RegisterModel(RegisterActivity registerActivity, PhotoCloudApiInterface api) {
        this.registerActivity = registerActivity;
        this.api = api;
    }

    Disposable createUser(String firstName, String lastName, String email, String password, final OnRegisterFinishedListener listener){
        if(firstName.isEmpty()){
            listener.onFirstNameError();
            return null;
        }
        if(lastName.isEmpty()){
            listener.onLastNameError();
            return null;
        }

        if(email.isEmpty() || (!email.isEmpty()) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            listener.onEmailError();
            return null;
        }

        if(password.isEmpty()){
            listener.onPasswordError();
            return null;
        }

        return api.createUser(new User(email, firstName, lastName, password))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ServiceResponse>() {
                    @Override
                    public void accept(ServiceResponse serviceResponse) throws Exception {
                        switch (serviceResponse.getCode()) {
                            case 1:
                                listener.onSuccess();
                                break;
                            case 0:
                                listener.onAlreadyExistingEmail();
                                break;
                            default:
                                listener.onFailure();
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(throwable!=null){
                            throwable.printStackTrace();
                        }
                        listener.onFailure();
                    }
                });
    }
}
