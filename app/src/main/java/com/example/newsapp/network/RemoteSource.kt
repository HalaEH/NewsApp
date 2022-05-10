package com.example.newsapp.network

import com.example.newsapp.model.NewsReponse

interface RemoteSource {
    suspend fun getNews(category: String, country: String): NewsReponse
    suspend fun getSearchResult(q: String, sortBy: String): NewsReponse
}