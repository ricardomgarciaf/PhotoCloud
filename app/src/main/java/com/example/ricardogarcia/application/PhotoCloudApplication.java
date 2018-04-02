package com.example.ricardogarcia.application;

import android.app.Application;

import com.example.ricardogarcia.application.builder.AppComponent;
import com.example.ricardogarcia.application.builder.AppModule;
import com.example.ricardogarcia.application.builder.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public class PhotoCloudApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appComponent= DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        LeakCanary.install(this);
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }


}
