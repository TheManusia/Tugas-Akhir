package com.ian.tugasakhir.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.source.remote.DataSource;
import com.ian.tugasakhir.data.source.remote.RemoteDataSource;
import com.ian.tugasakhir.network.ApiHelper;

import java.util.List;

public class Repository implements DataSource {
    private volatile static Repository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private Repository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(RemoteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(remoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Response> absen(String username) {
        MutableLiveData<Response> responses = new MutableLiveData<>();
        remoteDataSource.absen(username, new ApiHelper.AbsenCallback() {
            @Override
            public void onAbsen(Response response) {
                responses.postValue(response);
            }

            @Override
            public void onLoading(boolean status) {
                isLoading.postValue(status);
            }
        });
        return responses;
    }

    @Override
    public LiveData<Response> editProfile(String name, String username, String password, String newPass, String newPicture) {
        MutableLiveData<Response> responses = new MutableLiveData<>();
        remoteDataSource.editProfile(name, username, password, newPass, newPicture, new ApiHelper.EditCallback() {
            @Override
            public void onEdit(Response response) {
                responses.postValue(response);
            }

            @Override
            public void onLoading(boolean status) {
                isLoading.postValue(status);
            }
        });
        return responses;
    }

    @Override
    public LiveData<Response> login(String username, String password) {
        MutableLiveData<Response> responses = new MutableLiveData<>();
        remoteDataSource.login(username, password, new ApiHelper.LoginCallback() {
            @Override
            public void onLogin(Response response) {
                responses.postValue(response);
            }

            @Override
            public void onLoading(boolean status) {
                isLoading.postValue(status);
            }
        });
        return responses;
    }

    @Override
    public LiveData<List<Laporan>> getLaporan(String username) {
        MutableLiveData<List<Laporan>> response = new MutableLiveData<>();
        remoteDataSource.getLaporan(username, new ApiHelper.LaporanCallback() {
            @Override
            public void onGetLaporan(List<Laporan> laporan) {
                response.postValue(laporan);
            }

            @Override
            public void onLoading(boolean status) {
                isLoading.postValue(status);
            }
        });
        return response;
    }

    @Override
    public LiveData<Profile> getProfile(String username) {
        MutableLiveData<Profile> response = new MutableLiveData<>();
        remoteDataSource.getProfile(username, new ApiHelper.ProfileCallback() {
            @Override
            public void onGetProfile(Profile profile) {
                response.postValue(profile);
            }

            @Override
            public void onLoading(boolean status) {
                isLoading.postValue(status);
            }
        });
        return response;
    }

    @Override
    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}
