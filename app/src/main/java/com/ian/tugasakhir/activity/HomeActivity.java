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

import java.util.Calendar;

import static com.ian.tugasakhir.activity.LoginActivity.KEY_ID;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel viewModel;
    BadgeDrawable badge;
    ProfilePreference preference;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        preference = new ProfilePreference(this);
        id = getIntent().getStringExtra(KEY_ID);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        viewModel.setProfileData(id);

        badge = navView.getOrCreateBadge(R.id.navigation_absen);

        setData();
    }

    private void setData() {
        viewModel.getProfileData().observe(this, profile -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                badge.setVisible(false);
            } else {
                badge.setVisible(!profile.isAbsen());
            }
            preference.setProfile(profile);
        });

    }
}