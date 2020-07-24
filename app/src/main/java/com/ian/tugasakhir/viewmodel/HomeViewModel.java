package com.ian.tugasakhir.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.BuildConfig;
import com.ian.tugasakhir.model.Profile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<Profile> profileData = new MutableLiveData<>();

    public void setProfileData(String username) {
        String url = BuildConfig.SERVER + "get_profile.php";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);

                    Profile profile = new Profile();
                    profile.setId(jsonObject.getString("id"));
                    profile.setUsername(jsonObject.getString("username"));
                    profile.setGambar(jsonObject.getString("gambar"));
                    profile.setAbsen(jsonObject.getBoolean("absen"));
                    profile.setHadir(jsonObject.getInt("hadir"));
                    profile.setIzin(jsonObject.getInt("izin"));
                    profile.setAlpa(jsonObject.getInt("alpa"));
                    profile.setSession(true);
                    profileData.setValue(profile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<Profile> getProfileData() {
        return profileData;
    }
}