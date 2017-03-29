package com.rv150.lecture28march;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ivan on 28.03.17.
 */

public class ServiceHelper {
    private final OkHttpClient client = new OkHttpClient();

    private static final ServiceHelper instance = new ServiceHelper();

    private MyCallback callback;

    interface MyCallback {
        void onDataLoaded(String data);
    }

    public void setCallback(MyCallback callback) {
        this.callback = callback;
    }

    public static ServiceHelper getInstance() {
        return instance;
    }

    public void makeSearch(String query) {
        Request request = new Request.Builder()
                .url("http://api.github.com/search/repositories?q=" + query)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try  {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    if (callback != null) {
                        callback.onDataLoaded(responseBody.string());
                    }
                    responseBody.close();
                }
                catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), ex.getMessage());
                }
            }
        });
    }

}
