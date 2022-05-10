package com.example.newsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.model.RepositoryInterface
import java.lang.IllegalArgumentException

class NewsViewModelFactory  (private val _irepo: RepositoryInterface, private val _application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            NewsViewModel(_irepo,_application) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}