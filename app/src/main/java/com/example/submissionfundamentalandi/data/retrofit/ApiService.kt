package com.example.submissionfundamentalandi.data.retrofit

import com.example.submissionfundamentalandi.data.response.DetailUser
import com.example.submissionfundamentalandi.data.response.SearchUsers
import com.example.submissionfundamentalandi.data.response.UsersGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<List<UsersGithub>>

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String,
        @Header("Authorization") token: String
    ): Call<SearchUsers>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<DetailUser>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ) : Call<List<UsersGithub>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ) : Call<List<UsersGithub>>
}