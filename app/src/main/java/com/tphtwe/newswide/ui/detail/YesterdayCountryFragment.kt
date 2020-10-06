package com.tphtwe.newswide.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.ui.all.getFormatedNumber
import com.tphtwe.newswide.ui.all.percentage
import kotlinx.android.synthetic.main.fragment_yesterday_country.*
import java.text.NumberFormat
import java.util.*


class YesterdayCountryFragment : Fragment() {
    lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yesterday_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getResult().observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                var mild: Int = it.active - it.critical
                var mildTxt: String = ""
                if (mild <= 0) {
                    mildTxt = "0"
                } else {
                    mildTxt = NumberFormat.getNumberInstance(Locale.US).format(mild)
                }

                totalY.text="Total"
                yesterday.text="Yesterday"
                activeY.text="Active"
                criticalY.text="Critical"
                mildCY.text="Mild"
                rateY.text="Rate"

                var totAff: String = NumberFormat.getNumberInstance(Locale.US).format(it.cases)
                var totDeath: String = NumberFormat.getNumberInstance(Locale.US).format(it.deaths)
                var totRec: String = NumberFormat.getNumberInstance(Locale.US).format(it.recovered)
                var newAff: String = NumberFormat.getNumberInstance(Locale.US).format(it.todayCases)
                var newDeath: String =
                    NumberFormat.getNumberInstance(Locale.US).format(it.todayDeaths)
                var newRec: String =
                    NumberFormat.getNumberInstance(Locale.US).format(it.todayRecovered)
                var active: String = NumberFormat.getNumberInstance(Locale.US).format(it.active)
                var critical: String = NumberFormat.getNumberInstance(Locale.US).format(it.critical)
                var population: String =
                    NumberFormat.getNumberInstance(Locale.US).format(it.population)
                var shortPopulation: String = getFormatedNumber((it.population).toLong())

                var affRate: String = percentage((it.cases).toDouble(), (it.population).toDouble())
                var recRate: String = percentage((it.recovered).toDouble(), (it.cases).toDouble())
                var deathRate: String = percentage((it.deaths).toDouble(), (it.cases).toDouble())
                var actRate: String = percentage((it.active).toDouble(), (it.cases).toDouble())

                var rateCritical = ""
                if (it.critical == 0) {
                    rateCritical = "0"
                } else {
                    rateCritical = percentage((it.critical).toDouble(), (it.active).toDouble())
                }

                var rateMild: String =
                    percentage((mild).toDouble(), (it.active).toDouble()).toString()

                Picasso.get().load(it.countryInfo.flag).into(flagDetailY)
                populationTxtY.text = "Population --> $population"
                shortPopulationTxtY.text = "Approximate --> $shortPopulation"
                countryY.text = it.country
                affectedTY.text = "Cases --> ${totAff}"
                recoveredTY.text = "Recovered --> ${totRec}"
                deathsTY.text = "Deaths --> ${totDeath}"
                affectedNY.text = "Cases --> +${newAff}"
                deathsNY.text = "Deaths --> +${newDeath}"
                recoveredNY.text = "Recovered --> +${newRec}"
                activeValueY.text = active
                criticalValueY.text = critical
                mildValueY.text = mildTxt
                affectedRY.text = "Based On Population --> ${affRate}% Affected"
                recoveredRY.text = "Based On Cases --> ${recRate}% Recovered"
                deathsRY.text = "Based On Cases --> ${deathRate}% Deaths"
                activeRateY.text = "${actRate}%"
                criticalRateY.text = "${rateCritical}%"
                mildRateY.text = "${rateMild}%"
            }
        )
        detailViewModel.getProgress().observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { isLoading ->
                detailProgressY.visibility = if (isLoading) {
                    View.VISIBLE

                } else View.INVISIBLE
            }
        )

        detailViewModel.getErrorStatus().observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { status ->
                if (status) {
                    detailViewModel.getErrorMessage().observe(
                        viewLifecycleOwner, androidx.lifecycle.Observer { message ->
                            detailErrorY.text = message
                        }
                    )
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.loadResult(DetailFragment.name,true)
    }
    }

