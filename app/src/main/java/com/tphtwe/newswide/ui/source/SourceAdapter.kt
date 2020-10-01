package com.tphtwe.newswide.ui.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.source.Sources
import kotlinx.android.synthetic.main.item_source.view.*

class SourceAdapter(var sourceList:ArrayList<Sources>) :RecyclerView.Adapter<SourceAdapter.SourceViewHolder>(){
    var clickListener:ClickListener?=null
    fun setOnClickListener(clickListener:ClickListener){
        this.clickListener=clickListener
    }

    inner class SourceViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView),View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var source2:Sources
        fun bind(source:Sources){
            this.source2=source
            itemView.sourceName.text=source.sName
            itemView.itemSource.animation=AnimationUtils.loadAnimation(itemView.context,R.anim.left_to_right_anim2)
        }

        override fun onClick(p0: View?) {
            clickListener?.click(source2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.item_source,parent,false)
        return SourceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sourceList[position])
    }
    interface ClickListener{
        fun click(source: Sources)
    }
}