package com.example.ricardogarcia.application.builder;

import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Module
public class ApiServiceModule {

    @AppScope
    @Provides
    public PhotoCloudApiInterface apiServiceModule(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.5:8080/PhotoCloudService/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(PhotoCloudApiInterface.class);
    }
}
