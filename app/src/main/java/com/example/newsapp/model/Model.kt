package com.example.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "articles"
)
data class Articles(
    @PrimaryKey(autoGenerate = true)
   // var id: Int? = null,
    var id: Int,
    @SerializedName("source") val source: Source,
    @SerializedName("author") val author: String? = null ,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
    )

data class NewsReponse(
    @SerializedName("articles") val articles: List<Articles>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)

data class Source(
    @SerializedName("id") val id: Any,
    @SerializedName("name") val name: String
    )