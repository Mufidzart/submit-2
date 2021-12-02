package com.mufidz.githubusersubmission2.github.api

import com.mufidz.githubusersubmission2.github.model.DetailUserResponse
import com.mufidz.githubusersubmission2.github.model.UserGitHub
import com.mufidz.githubusersubmission2.github.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{name}")
    fun getUserDetail(
        @Path("name") username: String
    ): Call<DetailUserResponse>

    @GET("users/{name}/followers")
    fun getFollowers(
        @Path("name") username: String
    ): Call<ArrayList<UserGitHub>>

    @GET("users/{name}/following")
    fun getFollowing(
        @Path("name") username: String
    ): Call<ArrayList<UserGitHub>>
}