package com.ian.tugasakhir.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.ResponseLaporan;
import com.ian.tugasakhir.data.ResponseProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();

    private final ApiConfig apiConfig;

    public ApiHelper(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public void absen(String username, AbsenCallback callback) {
        callback.onLoading(true);
        Call<Response> client = apiConfig.getServices().absen(username);
        client.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onAbsen(response.body());
                    }
                } else {
                    Log.e(TAG, "absen: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.e(TAG, "absen: " + t.getMessage(), t);
            }
        });
    }

    public void editProfile(String name, String username, String password,
                            String newPass, String newPicture, EditCallback callback) {
        callback.onLoading(true);
        Call<Response> client = apiConfig.getServices().editProfile(name, username, password, newPass, newPicture);
        client.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onEdit(response.body());
                    }
                } else {
                    Log.e(TAG, "editProfile: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.e(TAG, "editProfile: " + t.getMessage(), t);
            }
        });
    }

    public void login(String username, String password, LoginCallback callback) {
        callback.onLoading(true);
        Call<Response> client = apiConfig.getServices().login(username, password);
        client.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onLogin(response.body());
                    }
                } else {
                    Log.e(TAG, "login: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.e(TAG, "login: " + t.getMessage(), t);
            }
        });

    }

    public void getLaporan(String username, LaporanCallback callback) {
        callback.onLoading(true);
        Call<ResponseLaporan> client = apiConfig.getServices().getLaporan(username);
        client.enqueue(new Callback<ResponseLaporan>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLaporan> call, @NonNull retrofit2.Response<ResponseLaporan> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        if (response.body().getStatus() == Response.OK)
                            callback.onGetLaporan(response.body().getLaporanList());
                } else {
                    Log.e(TAG, "getLaporan: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLaporan> call, @NonNull Throwable t) {
                Log.e(TAG, "getLaporan: " + t.getMessage(), t);
            }
        });
    }

    public void getProfile(String username, ProfileCallback callback) {
        callback.onLoading(true);
        Call<ResponseProfile> client = apiConfig.getServices().getProfile(username);
        client.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProfile> call, @NonNull retrofit2.Response<ResponseProfile> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == Response.OK)
                            callback.onGetProfile(response.body().getProfile());
                    }
                } else {
                    Log.e(TAG, "getProfile: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProfile> call, @NonNull Throwable t) {
                Log.e(TAG, "getProfile: " + t.getMessage(), t);
            }
        });
    }

    public interface AbsenCallback {
        void onAbsen(Response response);

        void onLoading(boolean status);
    }

    public interface EditCallback {
        void onEdit(Response response);

        void onLoading(boolean status);
    }

    public interface LoginCallback {
        void onLogin(Response response);

        void onLoading(boolean status);
    }

    public interface LaporanCallback {
        void onGetLaporan(List<Laporan> laporan);

        void onLoading(boolean status);
    }

    public interface ProfileCallback {
        void onGetProfile(Profile profile);

        void onLoading(boolean status);
    }
}
