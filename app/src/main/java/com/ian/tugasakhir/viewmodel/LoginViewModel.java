package com.ian.tugasakhir.viewmodel;

import android.util.Log;

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

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Response> responseList = new MutableLiveData<>();

    public void setResponseList(String username, String password) {
        String url = BuildConfig.SERVER + "proses_login.php";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.e("TAG", "onSuccess: " + result);
                    JSONObject responseObject = new JSONObject(result);

                    Response response = new Response();
                    response.setMessage(responseObject.getString("message"));
                    response.setSuccess(responseObject.getInt("success"));
                    responseList.setValue(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Response response = new Response();
                response.setMessage("Error: " + statusCode);
                response.setSuccess(0);
                responseList.setValue(response);
            }
        });
    }

    public LiveData<Response> getResponseList() {
        return responseList;
    }
}
