package com.example.newsapp.network

import android.util.Log
import com.example.newsapp.model.NewsReponse
import com.example.newsapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsClient: RemoteSource {
    override suspend fun getNews(category: String, country: String): NewsReponse {
        val newsService = RetrofitHelper.getInstance().create(NewsService::class.java)
        return newsService.getNews(category = category, country = country)
    }

    override suspend fun getSearchResult(q: String, sortBy: String): NewsReponse {
        val newsService = RetrofitHelper.getInstance().create(NewsService::class.java)
        return newsService.getSearchResult(q = q, sortBy = sortBy)
    }


    companion object{
        private var instance: NewsClient? = null
        fun getInstance(): NewsClient {
            return instance?: NewsClient()
        }
    }
}