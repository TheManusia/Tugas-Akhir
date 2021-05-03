package com.ian.tugasakhir.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ian.tugasakhir.R;
import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.network.retrofit.Network;
import com.ian.tugasakhir.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.pbLogin)
    ProgressBar pbLogin;

    String username;
    String password;

    public static final String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        pbLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnLogin)
    void login() {
        pbLogin.setVisibility(View.VISIBLE);
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        Network.getService()
                .login(username, password)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                        pbLogin.setVisibility(View.GONE);
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
                        pbLogin.setVisibility(View.GONE);
                        Log.e("TAG", "onFailure: " + t);
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}