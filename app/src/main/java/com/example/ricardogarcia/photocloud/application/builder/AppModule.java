package com.example.ricardogarcia.photocloud.application.builder;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context){
        this.context=context;
    }

    @Provides
    @AppScope
    Context provideContext(){
        return context;
    }

}
