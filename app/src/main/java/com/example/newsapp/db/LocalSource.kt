package com.example.newsapp.db

import androidx.lifecycle.LiveData
import com.example.newsapp.model.Articles

interface LocalSource {
    suspend fun upsert(articles: Articles): Long
    fun getAllArticles(): LiveData<List<Articles>>
    suspend fun deleteArticle(articles: Articles)

}