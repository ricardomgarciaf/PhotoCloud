package com.example.ricardogarcia.photocloud.application.builder;

import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
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
                .baseUrl("http://192.168.1.3:8080/PhotoCloudService/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(PhotoCloudApiInterface.class);
    }
}
