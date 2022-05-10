package com.example.newsapp.network

import com.example.newsapp.model.NewsReponse
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("category")
        category: String = "general",
        @Query("country")
        country: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsReponse

    //to search for everything
    @GET("everything")
    suspend fun getSearchResult(
        //searchQuery
        @Query("q")
        q: String,
        @Query("sortBy")
        sortBy: String = "publishedAt",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsReponse
}