package com.tphtwe.newswide.ui.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.allNews.Article
import kotlinx.android.synthetic.main.item_news.view.*
import java.lang.reflect.Array

class AllNewsAdapter(var listArticle:List<Article> = ArrayList<Article>()) :RecyclerView.Adapter<AllNewsAdapter.AllNewsViewHolder>(){
    var clickListener:ClickListener?=null
    fun setOnClickListener(clickListener:ClickListener){
        this.clickListener=clickListener
    }

    inner class AllNewsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView),View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var article2:Article
        fun bind(article: Article){
            this.article2=article
            var authorTxt=""
            if (article.author == null){
                authorTxt="Anonymous"
            }else{authorTxt=article.author.toString()}
            Picasso.get().load(article.urlToImage).into(itemView.newsImage)
            itemView.title.text=article.title
            itemView.source.text="Source: ${article.source.name}"
            itemView.author.text="Author: ${authorTxt}"
            itemView.date.text= dateFormat(article.publishedAt)
            itemView.ago.text= DateToTimeFormat(article.publishedAt)
            itemView.description.text=article.description
        }

        override fun onClick(view: View?) {
            clickListener?.click(article2)
        }
    }
    fun updateAllNews(listArticle: List<Article>){
        this.listArticle=listArticle
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return AllNewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    override fun onBindViewHolder(holder: AllNewsViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }
    interface ClickListener{
        fun click(article: Article)
    }
}