package com.tphtwe.newswide.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.ui.all.percentage
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.NumberFormat
import java.util.*


class DetailFragment : Fragment() {
    lateinit var detailViewModel: DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)


        detailViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                var mild: Int = it.active - it.critical
                var mildTxt: String = ""
                if (mild <= 0) {
                    mildTxt = "0"
                } else {
                    mildTxt = NumberFormat.getNumberInstance(Locale.US).format(mild)
                }

                var totAff:String= NumberFormat.getNumberInstance(Locale.US).format(it.cases)
                var totDeath:String= NumberFormat.getNumberInstance(Locale.US).format(it.deaths)
                var totRec:String= NumberFormat.getNumberInstance(Locale.US).format(it.recovered)
                var newAff:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayCases)
                var newDeath:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayDeaths)
                var newRec:String= NumberFormat.getNumberInstance(Locale.US).format(it.todayRecovered)
                var active:String= NumberFormat.getNumberInstance(Locale.US).format(it.active)
                var critical:String= NumberFormat.getNumberInstance(Locale.US).format(it.critical)

                var affRate:String= percentage((it.cases).toDouble(),(it.population).toDouble())
                var recRate:String= percentage((it.recovered).toDouble(),(it.cases).toDouble())
                var deathRate:String= percentage((it.deaths).toDouble(),(it.cases).toDouble())
                var actRate:String= percentage((it.active).toDouble(),(it.cases).toDouble())

                var rateCritical=""
                if (it.critical==0) {rateCritical= "0"}
                else{
                    rateCritical=percentage((it.critical).toDouble(),(it.active).toDouble())}

                var rateMild:String= percentage((mild).toDouble(),(it.active).toDouble()).toString()

                Picasso.get().load(it.countryInfo.flag).into(flagDetail)
                country.text = it.country
                affectedT.text = "Affected.. ${totAff}"
                recoveredT.text = "Recovered.. ${totRec}"
                deathsT.text = "Deaths.. ${totDeath}"
                affectedN.text = "Affected.. ${newAff}"
                deathsN.text = "Deaths.. ${newDeath}"
                recoveredN.text = "Recovered.. ${newRec}"
                activeValue.text = active
                criticalValue.text = critical
                mildValue.text = mildTxt
                affectedR.text="According To Population..${affRate}% Affected"
                recoveredR.text="According To Cases..${recRate}% Recovered"
                deathsR.text="According To Cases..${deathRate}% Deaths"
                activeRate.text="${actRate}%"
                criticalRate.text="${rateCritical}%"
                mildRate.text="${rateMild}%"

            })
        detailViewModel.getProgress().observe(
            viewLifecycleOwner, Observer<Boolean> { isLoading ->
                detailProgress.visibility = if (isLoading) {
                    View.VISIBLE

                } else View.INVISIBLE
            })
        detailViewModel.getErrorStatus().observe(
            viewLifecycleOwner, Observer { status ->
                if (status) {
                    detailViewModel.getErrorMessage().observe(
                        viewLifecycleOwner, Observer { message ->
                            detailError.text = message
                        })
                }
            })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        var messageArgs = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        var countryName = messageArgs!!.name
        (activity as AppCompatActivity).setSupportActionBar(toolbar_corona_detail)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title=countryName
        detailViewModel.loadResult(countryName,false)


        yesterday.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                detailViewModel.getProgress().observe(
                    viewLifecycleOwner, Observer<Boolean> { isLoading ->
                        detailProgress.visibility = if (isLoading) {
                            View.VISIBLE

                        } else View.INVISIBLE
                    })
                detailViewModel.getErrorStatus().observe(
                    viewLifecycleOwner, Observer { status ->
                        if (status) {
                            detailViewModel.getErrorMessage().observe(
                                viewLifecycleOwner, Observer { message ->
                                    detailError.text = message
                                })
                        }
                    })

                detailViewModel.loadResult(countryName,true)

            }
            else{
                detailViewModel.loadResult(countryName,false)
                detailViewModel.getProgress().observe(
                    viewLifecycleOwner, Observer<Boolean> { isLoading ->
                        detailProgress.visibility = if (isLoading) {
                            View.VISIBLE

                        } else View.INVISIBLE
                    })
                detailViewModel.getErrorStatus().observe(
                    viewLifecycleOwner, Observer { status ->
                        if (status) {
                            detailViewModel.getErrorMessage().observe(
                                viewLifecycleOwner, Observer { message ->
                                    detailError.text = message
                                })
                        }
                    })
            }
        })
    }


}