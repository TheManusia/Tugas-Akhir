package com.ian.tugasakhir.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.source.Repository;

public class HomeViewModel extends ViewModel {
    private String username;
    private final Repository repository;

    public HomeViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setProfileData(String username) {
        this.username = username;
    }

    public LiveData<Profile> getProfileData() {
        return repository.getProfile(username);
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }
}