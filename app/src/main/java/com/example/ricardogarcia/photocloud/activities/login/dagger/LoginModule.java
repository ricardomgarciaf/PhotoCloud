package com.example.ricardogarcia.photocloud.activities.login.dagger;

import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;
import com.example.ricardogarcia.photocloud.activities.login.core.LoginModel;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */
@Module
public class LoginModule {

    LoginActivity loginActivity;

    public LoginModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    /*@LoginScope
    @Provides
    LoginView provideView(LoginPresenter presenter){
        return new LoginView(loginActivity,presenter);
    }

    @LoginScope
    @Provides
    LoginPresenter providePresenter(LoginView view,LoginModel model){
        return new LoginPresenter(view, model);
    }*/

    @LoginScope
    @Provides
    LoginActivity provideContext(){
        return loginActivity;
    }

    @LoginScope
    @Provides
    LoginModel provideModel(PhotoCloudApiInterface api){
        return new LoginModel(loginActivity,api);
    }
}
