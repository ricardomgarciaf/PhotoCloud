package com.example.ricardogarcia.photocloud.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.ricardogarcia.photocloud.BuildConfig;
import com.example.ricardogarcia.photocloud.application.builder.AppComponent;
import com.example.ricardogarcia.photocloud.application.builder.AppModule;
import com.example.ricardogarcia.photocloud.application.builder.DaggerAppComponent;
import com.example.ricardogarcia.photocloud.application.builder.RoomModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public class PhotoCloudApplication extends Application {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static final String PREF_NAME = "SessionPreferences";
    public static int PRIVATE_MODE = 0;
    public static final String KEY_USER = "USER";

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else{
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {

                }
            });
        }

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        LeakCanary.install(this);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }


}
