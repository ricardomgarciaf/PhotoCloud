package com.example.ricardogarcia.photocloud.activities.register.core;

import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;

import io.reactivex.disposables.Disposable;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */

public class RegisterPresenter implements OnRegisterFinishedListener{
    private RegisterActivity view;
    private RegisterModel model;
    private Disposable disposable;

    public RegisterPresenter(RegisterActivity view, RegisterModel model) {
        this.view = view;
        this.model = model;
    }

    public void onDestroy(){
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public void registerUser(String firstName, String lastName, String email, String password){
        if(view!=null){
            view.showProgress();
        }
        disposable=model.createUser(firstName,lastName,email,password,this);
    }


    @Override
    public void onFirstNameError() {
        if(view!=null){
            view.hideProgress();
            view.setFirstNameError();
        }
    }

    @Override
    public void onLastNameError() {
        if(view!=null){
            view.hideProgress();
            view.setLastNameError();
        }
    }

    @Override
    public void onEmailError() {
        if(view!=null){
            view.hideProgress();
            view.setEmailError();
        }
    }

    @Override
    public void onPasswordError() {
        if(view!=null){
            view.hideProgress();
            view.setPasswordError();
        }
    }

    @Override
    public void onAlreadyExistingEmail() {
        if(view!=null){
            view.hideProgress();
            view.showAlreadyExistingEmail();
        }
    }

    @Override
    public void onSuccess() {
        if(view!=null){
            view.hideProgress();
            view.goHome();
        }
    }

    @Override
    public void onFailure() {
        if(view!=null){
            view.hideProgress();
            view.showError();
        }
    }
}
