package com.example.newsapp.db

import android.content.Context
import androidx.room.*
import com.example.newsapp.model.Articles

@Database(
    entities = [Articles::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object{
        private var instance: ArticleDatabase? = null
        //one thread at a time to access this method
        @Synchronized
        open fun getInstance(context: Context): ArticleDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "weather")
                        .build()
            }
            return instance as ArticleDatabase
        }
    }

}