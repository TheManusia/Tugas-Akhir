package com.ian.tugasakhir.ui.main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ian.tugasakhir.R;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.tools.ProfilePreference;
import com.ian.tugasakhir.ui.home.HomeActivity;
import com.ian.tugasakhir.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    Profile profile;
    private static final String KEY_ID = "ID";
    private static final String[] PERMISSION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProfilePreference preference = new ProfilePreference(this);

        profile = preference.getProfile();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(PERMISSION, PERMISSION_CODE);
        else
            ActivityCompat.requestPermissions(MainActivity.this,
                    PERMISSION,
                    PERMISSION_CODE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.fragment_fade_enter, R.anim.fragment_close_exit);
        if (profile.isSession()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra(KEY_ID, profile.getId());
            startActivity(intent, options.toBundle());
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class), options.toBundle());
        }
        finish();
    }
}