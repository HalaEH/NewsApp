package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Articles

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //insert and update an article
    suspend fun upsert(articles: Articles): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Articles>>

    @Delete
    suspend fun deleteArticle(articles: Articles)

}