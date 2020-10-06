package com.tphtwe.newswide.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.headlinesNews.HeadArticle
import com.tphtwe.newswide.ui.all.DateToTimeFormat
import com.tphtwe.newswide.ui.all.dateFormat
import kotlinx.android.synthetic.main.item_news.view.*
import java.lang.Exception

class CategoryNewsAdapter(var listArticle:List<HeadArticle> = ArrayList<HeadArticle>()) : RecyclerView.Adapter<CategoryNewsAdapter.CategoryNewsViewHolder>(){
    var clickListener:ClickListener?=null
    fun setOnClickListener(clickListener:ClickListener){
        this.clickListener=clickListener
    }

    inner class CategoryNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var article2: HeadArticle
        fun bind(article: HeadArticle){
            this.article2=article
            var authorTxt=""
            if (article.author == null){
                authorTxt="Anonymous"
            }else{authorTxt=article.author.toString()}
            var img=article.urlToImage
            if (img==null || img=="" || img==" "){img="https://i.pinimg.com/736x/2c/fa/39/2cfa392e8d0b5596ac8f5bf999470ac8.jpg"}
            else{img=article.urlToImage}

            itemView.imageProgress.visibility=View.VISIBLE
            Picasso.get().load(img).into(itemView.newsImage,object :Callback{
                override fun onSuccess() {
                    if (itemView.imageProgress!=null){itemView.imageProgress.visibility=View.GONE}
                    else{itemView.imageProgress.visibility=View.VISIBLE}
                }

                override fun onError(e: Exception?) {
                    itemView.newsImage.setImageResource(R.drawable.ic_close)
                    itemView.imageProgress.visibility=View.GONE
                }

            })
            itemView.title.text=article.title
            itemView.source.text="Source: ${article.source.name}"
            itemView.author.text="Author: ${authorTxt}"
            itemView.date.text= dateFormat(article.publishedAt)
            itemView.ago.text= DateToTimeFormat(article.publishedAt)
            itemView.description.text=article.description
            itemView.imageCard.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.left_to_right_anim)
            itemView.title.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.right_to_left_anim)
            itemView.source.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.right_to_left_anim)
            itemView.author.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.right_to_left_anim)
            itemView.ago.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.right_to_left_anim)
            itemView.description.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.bottom_to_up_anim)

        }

        override fun onClick(view: View?) {
            clickListener?.click(article2)
        }
    }
    fun updateAllNews(listArticle: List<HeadArticle>){
        this.listArticle=listArticle
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryNewsViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return CategoryNewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    override fun onBindViewHolder(holder: CategoryNewsViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }
    interface ClickListener{
        fun click(article: HeadArticle)
    }
}