package com.rv150.lecture28march;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements ServiceHelper.MyCallback{

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private EditText userQuery;

    private ServiceHelper serviceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userQuery = (EditText) findViewById(R.id.search_field);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        serviceHelper = ServiceHelper.getInstance();
        serviceHelper.setCallback(this);
    }


    public void makeSearch(View view) {
        String query = userQuery.getText().toString();
        serviceHelper.makeSearch(query);
    }


    @Override
    public void onDataLoaded(String data) {
        try {
            List<Repository> repositories = new ArrayList<>();
            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String repoName = jsonObject.getString("name");
                repositories.add(new Repository(repoName));
            }
            updateRecyclerView(repositories);
        }
        catch (JSONException ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }


    private void updateRecyclerView(List<Repository> repositories) {
        adapter.setData(repositories);
        recyclerView.swapAdapter(adapter, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceHelper.setCallback(null);
    }
}
