package com.ian.tugasakhir.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.databinding.ActivityHomeBinding;
import com.ian.tugasakhir.tools.ProfilePreference;

import java.util.Calendar;

import static com.ian.tugasakhir.ui.login.LoginActivity.KEY_ID;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    private HomeViewModel viewModel;
    private BadgeDrawable badge;
    private ProfilePreference preference;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);

        preference = new ProfilePreference(this);
        id = getIntent().getStringExtra(KEY_ID);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        viewModel.setProfileData(id);

        badge = binding.navView.getOrCreateBadge(R.id.navigation_absen);

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