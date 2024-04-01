package com.fajar.weathernews.data.remote.network

import com.fajar.weathernews.data.remote.response.ListNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=us")
    suspend fun getTopHeadline(
        @Query("apiKey") apiKey:String
    ): ListNewsResponse

    @GET("top-headlines?country=us&category=business")
    suspend fun getBusinessTopHeadline(
        @Query("apiKey") apiKey:String
    ): ListNewsResponse

    @GET("top-headlines?country=us&category=technology")
    suspend fun getTechTopHeadline(
        @Query("apiKey") apiKey:String
    ): ListNewsResponse

    @GET("top-headlines?country=us&category=science")
    suspend fun getScienceHeadline(
        @Query("apiKey") apiKey:String
    ): ListNewsResponse

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") query:String,
        @Query("apiKey") apiKey: String
    ): ListNewsResponse


}