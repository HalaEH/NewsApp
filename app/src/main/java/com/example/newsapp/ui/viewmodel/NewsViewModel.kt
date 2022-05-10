package com.example.newsapp.ui.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.NewsReponse
import com.example.newsapp.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel (private val _irepo: RepositoryInterface, private val _application: Application) : ViewModel() {

    private var _news: MutableLiveData<NewsReponse> = MutableLiveData()
    var news: LiveData<NewsReponse> = _news

    private var _sciNews: MutableLiveData<NewsReponse> = MutableLiveData()
    var sciNews: LiveData<NewsReponse> = _sciNews

    private var _businessNews: MutableLiveData<NewsReponse> = MutableLiveData()
    var businessNews: LiveData<NewsReponse> = _businessNews

    private var _sportsNews: MutableLiveData<NewsReponse> = MutableLiveData()
    var sportsNews: LiveData<NewsReponse> = _sportsNews

    private var _entertainmentNews: MutableLiveData<NewsReponse> = MutableLiveData()
    var entertainmentNews: LiveData<NewsReponse> = _entertainmentNews

    private var _healthNews: MutableLiveData<NewsReponse> = MutableLiveData()
    var healthNews: LiveData<NewsReponse> = _healthNews


    var country = PreferenceManager.getDefaultSharedPreferences(_application.applicationContext)
        .getString("country", "us")!!

    fun getNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "general",country = country)
            withContext(Dispatchers.IO){
                _news.postValue(newsReponse)
            }
        }
    }

    fun getSciNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "science",country = country)
            withContext(Dispatchers.IO){
                _sciNews.postValue(newsReponse)
            }
        }
    }

    fun getBusinessNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "business",country = country)
            withContext(Dispatchers.IO){
                _businessNews.postValue(newsReponse)
            }
        }
    }

    fun getSportsNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "sports",country = country)
            withContext(Dispatchers.IO){
                _sportsNews.postValue(newsReponse)
            }
        }
    }

    fun getEntertainmentNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "entertainment",country = country)
            withContext(Dispatchers.IO){
                _entertainmentNews.postValue(newsReponse)
            }
        }
    }


    fun getHealthNews(){
        viewModelScope.launch {
            val newsReponse = _irepo.getNews(category = "health",country = country)
            withContext(Dispatchers.IO){
                _healthNews.postValue(newsReponse)
            }
        }
    }

    fun getSearchResult(q: String, sortBy: String){
        viewModelScope.launch {
            val newsReponse = _irepo.getSearchResult(q= q, sortBy= sortBy)
            withContext(Dispatchers.IO){
                _news.postValue(newsReponse)
            }
        }
    }

    fun checkCountry(){
        country = PreferenceManager.getDefaultSharedPreferences(_application.applicationContext).getString("country","us")!!
    }

}