package com.tphtwe.newswide.ui.detailGlobal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.ui.all.getFormatedNumber
import com.tphtwe.newswide.ui.all.percentage
import com.tphtwe.newswide.ui.home.CoronaViewModel
import kotlinx.android.synthetic.main.fragment_global_detail.*
import java.text.NumberFormat
import java.util.*


class GlobalDetailFragment : Fragment() {
    lateinit var coronaViewModel: CoronaViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coronaViewModel=ViewModelProvider(this).get(CoronaViewModel::class.java)
        observeViewModel()
    }
    @SuppressLint("SetTextI18n")
    fun observeViewModel(){
        coronaViewModel.getResultGlobal().observe(
            viewLifecycleOwner, Observer {
                var mild: Int = it.active - it.critical
                var mildTxt: String = ""
                if (mild <= 0) {
                    mildTxt = "0"
                } else {
                    mildTxt = NumberFormat.getNumberInstance(Locale.US).format(mild)
                }


                val totAff:String= NumberFormat.getNumberInstance(Locale.US).format(it.cases)
                val totDeath:String= NumberFormat.getNumberInstance(Locale.US).format(it.deaths)
                val totRec:String= NumberFormat.getNumberInstance(Locale.US).format(it.recovered)
                val newAff:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayCases)
                val newDeath:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayDeaths)
                val newRec:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayRecovered)
                val active:String= NumberFormat.getNumberInstance(Locale.US).format(it.active)
                val critical:String= NumberFormat.getNumberInstance(Locale.US).format(it.critical)
                val population:String= NumberFormat.getNumberInstance(Locale.US).format(it.population)
                val shortPopulation:String= getFormatedNumber((it.population).toLong())

                val affRate:String= percentage((it.cases).toDouble(),(it.population).toDouble())
                val recRate:String= percentage((it.recovered).toDouble(),(it.cases).toDouble())
                val deathRate:String= percentage((it.deaths).toDouble(),(it.cases).toDouble())
                val actRate:String= percentage((it.active).toDouble(),(it.cases).toDouble())

                var rateCritical=""
                if (it.critical==0) {rateCritical= "0"}
                else{
                    rateCritical= percentage((it.critical).toDouble(),(it.active).toDouble())
                }

                val rateMild:String= percentage((mild).toDouble(),(it.active).toDouble()).toString()

                globalDetailImg.setImageResource(R.drawable.globe)
                populationTxtG.text="Population --> $population"
                shortPopulationTxtG.text="Approximate --> $shortPopulation"
                global.text = "World Wide"
                gAffectedT.text = "Cases --> ${totAff}"
                gRecoveredT.text = "Recovered --> ${totRec}"
                gDeathsT.text = "Deaths --> ${totDeath}"
                gAffectedN.text = "Cases --> +${newAff}"
                gDeathsN.text = "Deaths --> +${newDeath}"
                gRecoveredN.text = "Recovered --> +${newRec}"
                gActiveValue.text = active
                gCriticalValue.text = critical
                gMildValue.text = mildTxt
                gAffectedR.text="Based On Population --> ${affRate}% Affected"
                gRecoveredR.text="Based On Cases --> ${recRate}% Recovered"
                gDeathsR.text="Based On Cases --> ${deathRate}% Deaths"
                gActiveRate.text="${actRate}%"
                gCriticalRate.text="${rateCritical}%"
                gMildRate.text="${rateMild}%"
            }
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        totalG.text="Total"
        todayG.text="Today"
        activeG.text="Active"
        criticalG.text="Critical"
        mildCG.text="Mild"
        rateG.text="Rate"
        (activity as AppCompatActivity).setSupportActionBar(toolbar_global_detail)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="World Wide"
        coronaViewModel.loadResultGlobal(false)
        yesterdayG.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                todayG.text="Yesterday"
                coronaViewModel.loadResultGlobal(true)
            }
            else{
                todayG.text="Today"
                coronaViewModel.loadResultGlobal(false)
            }
        }
    }


}