package com.rest;

import com.repodb.Repository;


import java.util.List;

import retrofit.Call;

import retrofit.http.GET;

/**
 * Created by Valentyn on 25.11.2015.
 */
public interface RepositoryAPI {
    @GET("users/square/repos")
    Call<List<Repository>> getRepo();
}
