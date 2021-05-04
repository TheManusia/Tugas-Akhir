package com.ian.tugasakhir.di;

import com.ian.tugasakhir.data.source.Repository;
import com.ian.tugasakhir.data.source.remote.RemoteDataSource;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.network.ApiHelper;

public class Injection {
    public static Repository provideRepository(ApiConfig apiConfig) {
        RemoteDataSource remoteDataSource = new RemoteDataSource(new ApiHelper(apiConfig));
        return Repository.getInstance(remoteDataSource);
    }
}
