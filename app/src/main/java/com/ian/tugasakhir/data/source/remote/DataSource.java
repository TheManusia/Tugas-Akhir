package com.ian.tugasakhir.data.source.remote;

import androidx.lifecycle.LiveData;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;

import java.util.List;

public interface DataSource {
    LiveData<Response> absen(String username);

    LiveData<Response> editProfile(String name, String username, String password,
                                   String newPass, String newPicture);

    LiveData<Response> login(String username, String password);

    LiveData<List<Laporan>> getLaporan(String username);

    LiveData<Profile> getProfile(String username);

    LiveData<Boolean> isLoading();
}
