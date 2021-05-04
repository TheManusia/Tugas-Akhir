package com.ian.tugasakhir.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.source.Repository;

import lombok.Setter;

public class MapsViewModel extends ViewModel {
    private final Repository repository;

    @Setter
    private String username;

    public MapsViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Response> absen() {
        return repository.absen(username);
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }

}
