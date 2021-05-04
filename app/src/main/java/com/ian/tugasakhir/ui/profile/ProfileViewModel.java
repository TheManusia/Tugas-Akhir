package com.ian.tugasakhir.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.BuildConfig;
import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.source.Repository;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProfileViewModel extends ViewModel {
    private final Repository repository;
    private String username;

    public ProfileViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Laporan>> getListLaporan() {
        return repository.getLaporan(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}