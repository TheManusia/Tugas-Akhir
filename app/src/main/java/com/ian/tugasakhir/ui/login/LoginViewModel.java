package com.ian.tugasakhir.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.source.Repository;

import lombok.Setter;

public class LoginViewModel extends ViewModel {
    private final Repository repository;
    @Setter
    private String username;
    @Setter
    private String password;

    public LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }

    public LiveData<Response> login() {
        return repository.login(username, password);
    }
}
