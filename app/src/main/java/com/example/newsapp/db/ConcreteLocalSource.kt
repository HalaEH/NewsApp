package com.example.newsapp.db

import android.content.Context

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
}