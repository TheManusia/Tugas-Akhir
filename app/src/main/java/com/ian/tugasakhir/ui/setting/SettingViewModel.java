package com.ian.tugasakhir.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.source.Repository;

public class SettingViewModel extends ViewModel {
    private final Repository repository;

    public SettingViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }

    public LiveData<Response> editProfile(String name, String username, String password,
                                          String newPass, String newPicture) {
        return repository.editProfile(name, username, password, newPass, newPicture);
    }
}
