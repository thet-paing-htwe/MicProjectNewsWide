package com.tphtwe.newswide.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.AllCountryItem
import com.tphtwe.newswide.ui.all.getFormatedNumber
import kotlinx.android.synthetic.main.item_corona_country.view.*

class CoronaAdapter(var listCountry: List<AllCountryItem> = ArrayList<AllCountryItem>()) :
    RecyclerView.Adapter<CoronaAdapter.CoronaViewHolder>(), Filterable {
    var clickListener: ClickListener? = null
    var countryFilterList: List<AllCountryItem>
    init {
        countryFilterList = listCountry
    }

    fun setOnClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class CoronaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var allCountryItem2: AllCountryItem
        fun bind(allCountryItem: AllCountryItem) {
            this.allCountryItem2 = allCountryItem
            var pos:Int=countryFilterList.indexOf(listCountry[adapterPosition])
            Picasso.get().load(allCountryItem.countryInfo.flag).into(itemView.flagValue)
            itemView.nameValue.text = allCountryItem.country
            itemView.TaffectedValue.text = getFormatedNumber(allCountryItem.cases.toLong())
            itemView.TrecoveredValue.text = getFormatedNumber(allCountryItem.recovered.toLong())
            itemView.TdeathsValue.text = getFormatedNumber(allCountryItem.deaths.toLong())
            itemView.num.text = (pos+ 1).toString()
            if (allCountryItem.todayCases <= 0) {
                itemView.newAff.visibility = View.GONE
            } else {
                itemView.newAff.visibility = View.VISIBLE
                itemView.newAff.text = "+${getFormatedNumber(allCountryItem.todayCases.toLong())}"
            }

            if (allCountryItem.todayDeaths <= 0) {
                itemView.newDeaths.visibility = View.GONE
            } else {
                itemView.newDeaths.visibility = View.VISIBLE
                itemView.newDeaths.text =
                    "+${getFormatedNumber(allCountryItem.todayDeaths.toLong())}"
            }

            if (allCountryItem.todayRecovered <= 0) {
                itemView.newRec.visibility = View.GONE
            } else {
                itemView.newRec.visibility = View.VISIBLE
                itemView.newRec.text =
                    "+${getFormatedNumber(allCountryItem.todayRecovered.toLong())}"
            }
                itemView.itemConst.animation =
                    AnimationUtils.loadAnimation(itemView.context, R.anim.left_to_right_anim2)

        }

        override fun onClick(view: View?) {
            clickListener?.click(allCountryItem2)
        }


    }

    fun updateCountry(countryList: List<AllCountryItem>) {
        this.listCountry = countryList
        this.countryFilterList = countryList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoronaViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_corona_country, parent, false)
        return CoronaViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("size", listCountry.size.toString())
        return listCountry.size

    }

    override fun onBindViewHolder(holder: CoronaViewHolder, position: Int) {
        holder.bind(listCountry[position])
    }

    interface ClickListener {
        fun click(allCountryItem: AllCountryItem)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(char: CharSequence?): FilterResults {
                var charString: String = char.toString()
                if (charString.isEmpty()) {
                    listCountry = countryFilterList
                } else {
                    var filteredList: MutableList<AllCountryItem> = ArrayList()
                    for (row in countryFilterList) {

                        if (row.country.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                        listCountry = filteredList
                    }

                }
                val filterResults = FilterResults()
                filterResults.values = listCountry
                return filterResults

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(char: CharSequence?, res: FilterResults?) {
                listCountry = res?.values as List<AllCountryItem>
                notifyDataSetChanged()
            }

        }
    }

}