package com.ian.tugasakhir.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.network.retrofit.Network;
import com.ian.tugasakhir.databinding.ActivityLoginBinding;
import com.ian.tugasakhir.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private String username;

    public static final String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.pbLogin.setVisibility(View.GONE);
        binding.pbLogin.setOnClickListener(v -> login());
    }

    private void login() {
        binding.pbLogin.setVisibility(View.VISIBLE);
        username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        Network.getService()
                .login(username, password)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                        binding.pbLogin.setVisibility(View.GONE);
                        Response dataResponse = response.body();
                        if (dataResponse != null) {
                            if (dataResponse.getSuccess() > 0) {
                                showToast(dataResponse.getMessage());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra(KEY_ID, username);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast(dataResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                        binding.pbLogin.setVisibility(View.GONE);
                        Log.e("TAG", "onFailure: " + t);
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}