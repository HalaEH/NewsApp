package com.example.newsapp.model

import androidx.lifecycle.LiveData

interface RepositoryInterface {
    //over a network
    suspend fun getNews(category: String, country: String): NewsReponse
    suspend fun getSearchResult(q: String, sortBy: String): NewsReponse

    //Room
    suspend fun upsert(articles: Articles): Long
    fun getAllArticles(): LiveData<List<Articles>>
    suspend fun deleteArticle(articles: Articles)


}