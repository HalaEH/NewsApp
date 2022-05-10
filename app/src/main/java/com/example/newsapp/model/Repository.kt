package com.example.newsapp.model

import android.content.Context
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
        return remoteSource.getNews(category = category,country = country)
    }

    override suspend fun getSearchResult(q: String, sortBy: String): NewsReponse {
        return remoteSource.getSearchResult(q = q, sortBy = sortBy)
    }

}