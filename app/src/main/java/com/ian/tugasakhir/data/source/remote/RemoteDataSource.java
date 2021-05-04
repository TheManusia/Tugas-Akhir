package com.ian.tugasakhir.data.source.remote;

import com.ian.tugasakhir.network.ApiHelper;

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private final ApiHelper apiHelper;

    public RemoteDataSource(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public static RemoteDataSource getInstance(ApiHelper apiHelper) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource(apiHelper);
        return INSTANCE;
    }

    public void absen(String username, ApiHelper.AbsenCallback callback) {
        apiHelper.absen(username, callback);
    }

    public void editProfile(String name, String username, String password,
                            String newPass, String newPicture, ApiHelper.EditCallback callback) {
        apiHelper.editProfile(name, username, password, newPass, newPicture, callback);
    }

    public void login(String username, String password, ApiHelper.LoginCallback callback) {
        apiHelper.login(username, password, callback);
    }

    public void getLaporan(String username, ApiHelper.LaporanCallback callback) {
        apiHelper.getLaporan(username, callback);
    }

    public void getProfile(String username, ApiHelper.ProfileCallback callback) {
        apiHelper.getProfile(username, callback);
    }
}
