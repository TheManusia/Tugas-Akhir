package com.ian.tugasakhir.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.source.Repository;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private final Repository repository;
    private String username;

    public ProfileViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Laporan>> getListLaporan() {
        return repository.getLaporan(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }
}