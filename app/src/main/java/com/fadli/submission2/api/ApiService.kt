package com.fadli.submission2.api

import com.fadli.submission2.data.SearchResponse
import com.fadli.submission2.data.DataUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers (@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser (@Path("username") username: String): Call<DataUsers>

    @GET("users/{username}/following")
    fun getFollowing (@Path("username") username: String): Call<List<DataUsers>>

    @GET("users/{username}/followers")
    fun getFollowers (@Path("username") username: String): Call<List<DataUsers>>

}