package com.tphtwe.newswide.ui.vaccine

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.vaccine.Data
import com.tphtwe.newswide.ui.all.clearText
import com.tphtwe.newswide.ui.all.textanimation
import kotlinx.android.synthetic.main.item_vaccine.view.*
import kotlinx.coroutines.withContext

class VaccineAdapter(var listVaccine: List<Data> = ArrayList<Data>()) :
    RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>() {
    class VaccineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: Data) {
            itemView.no.text=(adapterPosition+1).toString()
            itemView.phase.text = "Trial Phase\n${(data.trialPhase)}"

            itemView.prjName.text = "Trial Name\n${(data.candidate)}"
            var listSponser = ArrayList<String>()
            for (element in data.sponsors) {
                listSponser.add(element)
            }
            itemView.sponser.text = "Sponsors\n${clearText(listSponser.toString())}"

            var listInstitution = ArrayList<String>()
            for (element1 in data.institutions) {
                listInstitution.add(element1)
            }
            itemView.institution.text = "Institutions\n${clearText(listInstitution.toString())}"
            itemView.mechanism.text = "Mechanism\n${clearText(data.mechanism)}"
            itemView.details.text = clearText(data.details)
            itemView.details.maxLines = 3
            itemView.more.visibility=View.VISIBLE
            itemView.less.visibility=View.GONE

            itemView.more.setOnClickListener {
                textanimation(itemView.details)
                itemView.more.visibility = View.GONE
                itemView.less.visibility=View.VISIBLE
            }

            itemView.less.setOnClickListener {
                textanimation(itemView.details)
                itemView.more.visibility = View.VISIBLE
                itemView.less.visibility=View.GONE
            }
            itemView.details.setOnClickListener {
                if (itemView.details.maxLines==3){
                    itemView.more.visibility = View.GONE
                    itemView.less.visibility=View.VISIBLE
                    textanimation(itemView.details)
                }
                else{
                    itemView.more.visibility = View.VISIBLE
                    itemView.less.visibility=View.GONE
                    textanimation(itemView.details)                }
            }
            itemView.phaseCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.left_to_right_anim)
            itemView.nameCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.right_to_left_anim)
            itemView.mechCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.left_to_right_anim)
            itemView.sponCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.right_to_left_anim)
            itemView.instCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.left_to_right_anim)
            itemView.detailCard.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.right_to_left_anim)


        }
    }

    fun updateVaccine(vaccineList: List<Data>) {
        this.listVaccine = vaccineList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_vaccine, parent, false)
        return VaccineViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listVaccine.size
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        holder.bind(listVaccine[position])

    }
}