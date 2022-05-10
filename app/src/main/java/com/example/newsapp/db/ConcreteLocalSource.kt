package com.example.newsapp.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.model.Articles

class ConcreteLocalSource: LocalSource {
    var dao: ArticleDao? = null
    constructor(context: Context){
        val database: ArticleDatabase = ArticleDatabase.getInstance(context)!!
        dao = database.getArticleDao()!!
    }

    companion object{
        var localSource: ConcreteLocalSource? = null
        fun getInstance(context: Context): ConcreteLocalSource{
            if(localSource == null)
                localSource = ConcreteLocalSource(context)
            return localSource as ConcreteLocalSource
        }
    }

    override suspend fun upsert(articles: Articles): Long {
        return dao!!.upsert(articles)
    }

    override fun getAllArticles(): LiveData<List<Articles>> {
        return dao!!.getAllArticles()
    }

    override suspend fun deleteArticle(articles: Articles) {
        return deleteArticle(articles)
    }


}