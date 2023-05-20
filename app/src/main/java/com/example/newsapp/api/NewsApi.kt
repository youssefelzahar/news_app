package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utilite.Constances.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingFun(
        @Query("country")
        countrycode: String ="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        api_key:String=API_KEY

    ): Response<NewsResponse>
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

}