package com.ian.tugasakhir.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.databinding.ActivityLoginBinding;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.ui.home.HomeActivity;
import com.ian.tugasakhir.viewmodel.ViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private String username;

    public static final String KEY_ID = "ID";
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.pbLogin.setVisibility(View.GONE);
        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
        binding.btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        binding.pbLogin.setVisibility(View.VISIBLE);
        username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        viewModel.setUsername(username);
        viewModel.setPassword(password);
        viewModel.login().observe(this, response -> {
            if (response.getStatus() == Response.OK) {
                showToast(response.getMessage());
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(KEY_ID, username);
                startActivity(intent);
                finish();
            } else {
                showToast(response.getMessage());
            }
        });

        viewModel.isLoading().observe(this, aBoolean -> {
            if (aBoolean)
                binding.pbLogin.setVisibility(View.VISIBLE);
            else
                binding.pbLogin.setVisibility(View.GONE);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}