package com.example.ricardogarcia.photocloud.activities.register.core;

import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.model.User;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */

public class RegisterModel {
    private RegisterActivity registerActivity;
    private PhotoCloudApiInterface api;
    private UserDataSource userDataSource;

    public RegisterModel(RegisterActivity registerActivity, PhotoCloudApiInterface api, UserDataSource userDataSource) {
        this.registerActivity = registerActivity;
        this.api = api;
        this.userDataSource=userDataSource;
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
                    Timber.d("ServiceResponse "+serviceResponse);
                    if(serviceResponse.getCode()==1){
                        userDataSource.addItem(new com.example.ricardogarcia.photocloud.repository.entity.User(String.valueOf(serviceResponse.getObject()),email));
                    }
                    return Observable.fromArray(serviceResponse.getCode());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    switch (i) {
                        case 1:
                            PhotoCloudApplication.editor.putString(PhotoCloudApplication.KEY_USER,email);
                            PhotoCloudApplication.editor.commit();
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
