package com.ian.tugasakhir.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.network.retrofit.Network;
import com.ian.tugasakhir.databinding.ActivitySettingBinding;
import com.ian.tugasakhir.tools.Converter;
import com.ian.tugasakhir.tools.ProfilePreference;
import com.ian.tugasakhir.ui.home.HomeActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;

import static com.ian.tugasakhir.ui.login.LoginActivity.KEY_ID;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    private Bitmap gambar;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ProfilePreference preference = new ProfilePreference(this);
        profile = preference.getProfile();

        Glide.with(this)
                .load(profile.getGambar())
                .apply(new RequestOptions().override(150, 150))
                .error(R.drawable.ic_baseline_person_24)
                .into(binding.civAvatarEdit);

        binding.edtName.setText(profile.getUsername());

        setSupportActionBar(binding.toolbarSetting);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(profile.getId());
        }
        binding.pbEdit.setVisibility(View.GONE);

        binding.btnEdit.setOnClickListener(v -> validation());
        binding.btnUpload.setOnClickListener(v -> getGambar());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Masukkan Password Anda");

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(editText);

        builder.setPositiveButton("Simpan", (dialog, which) -> doEdit(editText.getText().toString().trim(), dialog));

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void doEdit(String password, DialogInterface dialog) {
        binding.pbEdit.setVisibility(View.VISIBLE);
        String username = profile.getId();
        String name = binding.edtName.getText().toString().trim();
        String newPass = binding.edtNewPass.getText().toString().trim();
        String confPass = binding.edtConfPass.getText().toString().trim();
        String newPicture = Converter.bitmapToString(gambar);

        if (!newPass.equals(confPass)) {
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
        } else {
            Network.getService()
                    .editProfile(name, username, password, newPass, newPicture)
                    .enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                            Response responseData = response.body();
                            if (responseData != null) {
                                if (responseData.getSuccess() > 0) {
                                    Toast.makeText(SettingActivity.this, "Berhasil mengubah profile", Toast.LENGTH_SHORT).show();
                                    profile.setUsername(name);
                                    profile.setId(username);
                                    Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                                    intent.putExtra(KEY_ID, username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(SettingActivity.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            binding.pbEdit.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                            Toast.makeText(SettingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onFailure: " + t);
                            binding.pbEdit.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
        }

    }

    private void getGambar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri imageUri = null;
                if (data != null) {
                    imageUri = data.getData();
                }

                InputStream imageStream = null;
                if (imageUri != null) {
                    imageStream = getContentResolver().openInputStream(imageUri);
                }

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(this)
                        .load(selectedImage)
                        .apply(new RequestOptions())
                        .into(binding.civAvatarEdit);
                gambar = selectedImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal upload gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}