package com.example.searchapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.searchapp.*;
import com.example.searchapp.adapters.CustomAdapter;
import com.example.searchapp.clients.APIClient;
import com.example.searchapp.clients.APIInterface;
import com.example.searchapp.data.MyDbHandler;
import com.example.searchapp.models.University;
import com.example.searchapp.services.ForegroundService;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;

    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_row_item);
        recyclerView = findViewById(R.id.recyclerView);
        Activity activity = this;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //DB Connection
        MyDbHandler db = getMyDbHandler();
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        List<University> oldListUniversities = db.getUniversities();
        db.close();
        if (oldListUniversities != null && !oldListUniversities.isEmpty()) {
            CustomAdapter customAdapter = new CustomAdapter(oldListUniversities);
            customAdapter.activity = activity;
            customAdapter.context = activity;
            customAdapter.bundle = savedInstanceState;
            recyclerView.setAdapter(customAdapter);
        }
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<List<University>> call = apiInterface.doGetUserList();
                call.enqueue(new Callback<List<University>>() {

                    @Override
                    public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                        MyDbHandler db = getMyDbHandler();
                        db.truncateDb();
                        List<University> universities = response.body();
                        db.addUniv(universities);
                        CustomAdapter customAdapter = new CustomAdapter(universities);
                        customAdapter.activity = activity;
                        customAdapter.context = activity;
                        customAdapter.bundle = savedInstanceState;
                        // Restore state
                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                        recyclerView.setAdapter(customAdapter);
                        db.close();
                    }

                    @Override
                    public void onFailure(Call<List<University>> call, Throwable t) {
                        call.cancel();
                    }

                });
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(refresh, 10000);
    }

    @NotNull
    private MyDbHandler getMyDbHandler() {
        MyDbHandler db = new MyDbHandler(this);
        return db;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }
}
