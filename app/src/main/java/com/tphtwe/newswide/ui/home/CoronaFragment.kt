package com.tphtwe.newswide.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.AllCountryItem
import com.tphtwe.newswide.ui.all.getFormatedNumber
import com.tphtwe.newswide.ui.home.adapter.CoronaAdapter
import kotlinx.android.synthetic.main.fragment_corona.*
import kotlinx.android.synthetic.main.item_sort.*

@Suppress("DEPRECATION")
class CoronaFragment : Fragment(), CoronaAdapter.ClickListener {
    private lateinit var coronaViewModel: CoronaViewModel
    lateinit var coronaAdapter: CoronaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_corona, container, false)
        return root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coronaViewModel = ViewModelProvider(this).get(CoronaViewModel::class.java)
        coronaAdapter = CoronaAdapter()
        coronaAdapter.setOnClickListener(this)
        var animate=AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)
        coronaRecycler.animation=animate
        animate.duration=500
        coronaRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coronaAdapter
        }

        observeViewModel()
    }


    @SuppressLint("SetTextI18n")
    fun observeViewModel() {
        coronaViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                coronaAdapter.updateCountry(it)
            }
        )
        coronaViewModel.getProgress().observe(
            viewLifecycleOwner, Observer<Boolean> { isLoading ->
                coronaProgress.visibility = if (isLoading) {
                    View.VISIBLE
                } else {View.GONE }
            })
        coronaViewModel.getErrorStatus().observe(
            viewLifecycleOwner, Observer { status ->
                if (status) {
                    coronaViewModel.getErrorMessage().observe(
                        viewLifecycleOwner, Observer { message ->
                            coronaError.text = message
                        })
                }
            })
//        coronaViewModel.getRefresh().observe(
//            viewLifecycleOwner, Observer <Boolean>{isRefreshing->
//                swipeRefresh.isRefreshing = isRefreshing
//            }
//        )

        coronaViewModel.getResultGlobal().observe(
            viewLifecycleOwner, Observer {
                Taffected.text= "${getFormatedNumber((it.cases).toLong())}\nAffected"
                Tdeaths.text="${getFormatedNumber((it.deaths).toLong())}\nDeaths"
                Trecovered.text="${getFormatedNumber((it.recovered).toLong())}\nRecovered"
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onResume() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (activity as AppCompatActivity).setSupportActionBar(toolbar_corona)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="Corona Tracker"

        coronaViewModel.loadResultGlobal(false)
        coronaViewModel.loadResult("cases")

        super.onResume()
        swipeRefresh.setOnRefreshListener {
            coronaViewModel.loadResultGlobal(false)
            coronaViewModel.loadResult("cases")
            observeViewModel()
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }
            }, 5000)
        }
//        swipeRefresh.setColorSchemeColors(R.color.materialGreen,R.color.materialBlue,R.color.materialRed);
        swipeRefresh.setColorSchemeResources(R.color.materialGreen,R.color.materialBlue,R.color.materialRed)
            country_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("tsubmit", query.toString())
                coronaAdapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("tchange", newText.toString())
                    var mAdapter=coronaAdapter.filter
                    mAdapter.filter(newText)
                    return false
                }
            })

        sort.setOnClickListener {
            var sortDialog = LayoutInflater.from(context).inflate(R.layout.item_sort, null)
            val showBuider = AlertDialog.Builder(context)
            showBuider.apply {
                setView(sortDialog)
            }
            val showAlertDialog = showBuider.show()
            showAlertDialog.radio_group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                showAlertDialog.dismiss()
                when (i) {
                    R.id.nameAsc -> coronaViewModel.loadResult("name")

                    R.id.nameDsc -> coronaViewModel.loadResult("country")

                    R.id.maxAffected -> coronaViewModel.loadResult("cases")

                    R.id.maxDeaths -> coronaViewModel.loadResult("deaths")

                    R.id.maxRecovered -> coronaViewModel.loadResult("recovered")

                    R.id.affToday -> coronaViewModel.loadResult("todayCases")

                    R.id.deathToday -> coronaViewModel.loadResult("todayDeaths")

                    R.id.recToday -> coronaViewModel.loadResult("todayRecovered")

                    else -> coronaViewModel.loadResult("cases")

                }

            })
        }
        headerCard.setOnClickListener {
            findNavController().navigate(R.id.action_nav_corona_to_globalDetailFragment)
        }

    }
//    fun radio_button_click(view: View) {
//        if (view is RadioButton) {
//            val checked = view.isChecked
//            when(view.getId()){
//                R.id.nameAsc->if (checked){
//                    coronaViewModel.loadResult("name")
//                }
//                R.id.nameDsc->if (checked){
//                    coronaViewModel.loadResult("country")
//                }
//            }
//        }
//
//    }




override fun click(allCountryItem: AllCountryItem) {
    var action=CoronaFragmentDirections.actionNavCoronaToDetailFragment(allCountryItem.country)
    findNavController().navigate(action)

}
}