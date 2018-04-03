package com.example.ricardogarcia.photocloud.activities.register.dagger;

import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;
import com.example.ricardogarcia.photocloud.activities.register.core.RegisterModel;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */
@Module
public class RegisterModule {
    RegisterActivity registerActivity;

    public RegisterModule(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    @RegisterScope
    @Provides
    RegisterActivity provideContext(){
        return registerActivity;
    }

    @RegisterScope
    @Provides
    RegisterModel provideModel(PhotoCloudApiInterface api){
        return new RegisterModel(registerActivity,api);
    }
}
