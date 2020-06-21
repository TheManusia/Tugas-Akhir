package com.ian.tugasakhir.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.BuildConfig;
import com.ian.tugasakhir.model.Laporan;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Laporan>> listLaporan = new MutableLiveData<>();

    public LiveData<ArrayList<Laporan>> getListLaporan() {
        return listLaporan;
    }

    public void setListLaporan(String username) {
        final ArrayList<Laporan> list = new ArrayList<>();

        String url = BuildConfig.SERVER + "get_laporan.php";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray resultArray = new JSONArray(result);

                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject user = resultArray.getJSONObject(i);
                        Laporan laporan = new Laporan();
                        laporan.setTanggal(user.getString("tanggal"));
                        laporan.setType(user.getString("type"));
                        list.add(laporan);
                    }
                    listLaporan.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
}