package com.example.newsapp.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.db.LocalSource
import com.example.newsapp.network.RemoteSource

class Repository(var context: Context, var localSource: LocalSource,var remoteSource: RemoteSource): RepositoryInterface {
    companion object{
        private var instance: Repository? = null
        fun getInstance(context: Context, localSource: LocalSource,remoteSource: RemoteSource): Repository{
            return instance?: Repository(context,localSource,remoteSource)
        }
    }

    override suspend fun getNews(category: String, country: String): NewsReponse {
        val response =remoteSource.getNews(category,country)
//        if (response!=null){
//            for(article in response.articles){
//                localSource.upsert(article)
//            }
//        }

        return remoteSource.getNews(category = category,country = country)
    }

    override suspend fun getSearchResult(q: String, sortBy: String): NewsReponse {
        return remoteSource.getSearchResult(q = q, sortBy = sortBy)
    }

    override suspend fun upsert(articles: Articles): Long {
        return localSource.upsert(articles)
    }

    override fun getAllArticles(): LiveData<List<Articles>> {
        return localSource.getAllArticles()
    }

    override suspend fun deleteArticle(articles: Articles) {
        return localSource.deleteArticle(articles)
    }

}