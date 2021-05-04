package com.ian.tugasakhir.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ian.tugasakhir.data.source.Repository;
import com.ian.tugasakhir.di.Injection;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.ui.home.HomeViewModel;
import com.ian.tugasakhir.ui.login.LoginViewModel;
import com.ian.tugasakhir.ui.maps.MapsViewModel;
import com.ian.tugasakhir.ui.profile.ProfileViewModel;
import com.ian.tugasakhir.ui.setting.SettingViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final Repository repository;

    private ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    public static ViewModelFactory getInstance(ApiConfig apiConfig) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(apiConfig));
                }
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository);
        } else if (modelClass.isAssignableFrom(MapsViewModel.class)) {
            return (T) new MapsViewModel(repository);
        } else if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(repository);
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(repository);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
