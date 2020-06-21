package com.ian.tugasakhir.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ian.tugasakhir.BuildConfig;
import com.ian.tugasakhir.model.Response;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapViewModel extends ViewModel {
    private MutableLiveData<Response> responseData = new MutableLiveData<>();

    public void setResponse(String username) {
        String url = BuildConfig.SERVER + "proses_absen.php";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);

                    Response response = new Response();
                    response.setSuccess(jsonObject.getInt("success"));
                    response.setMessage(jsonObject.getString("message"));
                    responseData.setValue(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<Response> getResponseData() {
        return responseData;
    }
}
