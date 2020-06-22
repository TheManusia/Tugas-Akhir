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

public class SettingViewModel extends ViewModel {
    private MutableLiveData<Response> responseList = new MutableLiveData<>();

    public LiveData<Response> getResponseList() {
        return responseList;
    }

    public void setResponse(String username, String nama, String gambar, String password, String newpass) {
        String url = BuildConfig.SERVER + "proses_edit.php";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("nama", nama);
        params.put("username", username);
        params.put("password", password);
        params.put("newpassword", newpass);
        params.put("gambar", gambar);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject resultObj = new JSONObject(result);

                    Response response = new Response();
                    response.setMessage(resultObj.getString("message"));
                    response.setSuccess(resultObj.getInt("success"));
                    responseList.setValue(response);
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
