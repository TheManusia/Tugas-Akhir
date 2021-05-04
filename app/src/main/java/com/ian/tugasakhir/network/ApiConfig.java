package com.ian.tugasakhir.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ian.tugasakhir.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public Service getServices() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(Service.class);
    }
}
