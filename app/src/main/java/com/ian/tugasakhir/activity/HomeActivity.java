package com.ian.tugasakhir.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.preference.ProfilePreference;
import com.ian.tugasakhir.viewmodel.HomeViewModel;

import static com.ian.tugasakhir.activity.LoginActivity.KEY_USERNAME;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel viewModel;
    BadgeDrawable badge;
    ProfilePreference preference;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        preference = new ProfilePreference(getApplicationContext());

        if (getIntent() != null) {
            username = getIntent().getStringExtra(KEY_USERNAME);
        }

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        viewModel.setProfileData(username);

        badge = navView.getOrCreateBadge(R.id.navigation_absen);

        setData();
    }

    private void setData() {
        viewModel.getProfileData().observe(this, profile -> preference.setProfile(profile));
    }
}