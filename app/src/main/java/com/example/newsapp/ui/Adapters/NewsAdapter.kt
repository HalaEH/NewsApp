package com.example.newsapp.ui.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.model.Articles

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<Articles> = listOf()

    class NewsViewHolder(val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(articles: List<Articles>){
        this.newsList = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var currentItem = newsList[position]

        holder.binding.apply {
            title.text = currentItem.title
            name.text = currentItem.source.name
            if(currentItem.description!=null && currentItem.description.length>85){
                description.text = "${currentItem.description.slice(IntRange(0,85))}...."
            }else{
                description.text = currentItem.description
            }
            author.text = currentItem.author
            date.text = currentItem.publishedAt.slice(IntRange(0,9))

            cardview.setOnClickListener{
                val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.url))
                context.startActivity(browserIntent)
            }
            shareIcon.setOnClickListener{
                shareArticle(currentItem)
            }
        }
        Glide.with(context).load(currentItem.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    private fun shareArticle(article: Articles){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_TEXT,"${article.title} : ${article.url}")
        context.startActivity(Intent.createChooser(sharingIntent,"Share Article"))
    }

}