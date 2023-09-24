package com.example.searchapp.clients;

import com.example.searchapp.models.University;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface APIInterface {
    @GET("/search")
    Call<List<University>> doGetUserList();
}
