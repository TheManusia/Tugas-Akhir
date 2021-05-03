package com.ian.tugasakhir.data.network.retrofit;

import com.ian.tugasakhir.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static Service getService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
    }
}
