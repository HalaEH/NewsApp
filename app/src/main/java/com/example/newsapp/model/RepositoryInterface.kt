package com.example.newsapp.model

interface RepositoryInterface {
    suspend fun getNews(category: String, country: String): NewsReponse
    suspend fun getSearchResult(q: String, sortBy: String): NewsReponse
}