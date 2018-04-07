package com.example.ricardogarcia.photocloud.activities.register.core;

import com.example.ricardogarcia.photocloud.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.model.User;
import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.persistence.database.AppDatabase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
                .flatMap((ServiceResponse serviceResponse) -> {
                    if(serviceResponse.getCode()==1){
                        AppDatabase mdb=AppDatabase.getAppDatabase(registerActivity);
                        mdb.userDao().insert(new com.example.ricardogarcia.photocloud.persistence.entity.User(String.valueOf(serviceResponse.getObject()),email));
                    }
                    return Observable.fromArray(serviceResponse.getCode());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    switch (i) {
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
                }, throwable -> {
                    if(throwable!=null){
                        throwable.printStackTrace();
                    }
                    listener.onFailure();
                });
    }

    public void goToHome(){
        registerActivity.goHome();
    }
}
