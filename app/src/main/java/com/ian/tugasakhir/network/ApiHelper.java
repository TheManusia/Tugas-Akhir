package com.ian.tugasakhir.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;

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
        Call<List<Laporan>> client = apiConfig.getServices().getLaporan(username);
        client.enqueue(new Callback<List<Laporan>>() {
            @Override
            public void onResponse(@NonNull Call<List<Laporan>> call, @NonNull retrofit2.Response<List<Laporan>> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onGetLaporan(response.body());
                    }
                } else {
                    Log.e(TAG, "getLaporan: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Laporan>> call, @NonNull Throwable t) {
                Log.e(TAG, "getLaporan: " + t.getMessage(), t);
            }
        });
    }

    public void getProfile(String username, ProfileCallback callback) {
        callback.onLoading(true);
        Call<Profile> client = apiConfig.getServices().getProfile(username);
        client.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull retrofit2.Response<Profile> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onGetProfile(response.body());
                    }
                } else {
                    Log.e(TAG, "getProfile: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
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
