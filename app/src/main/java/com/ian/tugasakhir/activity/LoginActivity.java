package com.ian.tugasakhir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ian.tugasakhir.R;
import com.ian.tugasakhir.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.pbLogin)
    ProgressBar pbLogin;

    LoginViewModel viewModel;
    String username;

    public static final String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        pbLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnLogin)
    void login() {
        pbLogin.setVisibility(View.VISIBLE);
        username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        viewModel.setResponseList(username, password);
        doLogin();
    }

    private void doLogin() {
        viewModel.getResponseList().removeObservers(this);
        viewModel.getResponseList().observe(this, response -> {
            pbLogin.setVisibility(View.GONE);
            if (response.getSuccess() > 0) {
                showToast("Berhasil Login");
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(KEY_ID, username);
                startActivity(intent);
                finish();
            } else {
                showToast(response.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}