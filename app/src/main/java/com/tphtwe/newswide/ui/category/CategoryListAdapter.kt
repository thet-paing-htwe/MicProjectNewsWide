package com.tphtwe.newswide.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryListAdapter(var categoryList:ArrayList<Category>) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>(){
    var clickListener:ClickListener?=null
    fun setOnClickListener(clickListener:ClickListener){
        this.clickListener=clickListener
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var category2: Category
        fun bind(category: Category){
            this.category2=category
            itemView.category.text=category.Cname
            itemView.catImg.setImageResource(category.img)
        }

        override fun onClick(p0: View?) {
            clickListener?.click(category2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }
    interface ClickListener{
        fun click(category: Category)
    }
}