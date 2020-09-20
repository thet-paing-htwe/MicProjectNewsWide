package com.tphtwe.newswide.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.AllCountryItem
import com.tphtwe.newswide.ui.corona.adapter.CoronaAdapter
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_all_news.*
import kotlinx.android.synthetic.main.fragment_corona.*
import kotlinx.android.synthetic.main.item_sort.*

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
        coronaRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coronaAdapter
        }
        observeViewModel()
    }


    fun observeViewModel() {
        coronaViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                coronaAdapter.updateCountry(it)
            }
        )
//        coronaViewModel.getProgress().observe(
//            viewLifecycleOwner, Observer<Boolean> { isLoading ->
//                coronaProgress.visibility = if (isLoading) {
//                    View.VISIBLE
//                } else View.INVISIBLE
//            })
//        coronaViewModel.getErrorStatus().observe(
//            viewLifecycleOwner, Observer { status ->
//                if (status) {
//                    coronaViewModel.getErrorMessage().observe(
//                        viewLifecycleOwner, Observer { message ->
//                            coronaError.text = message
//                        })
//                }
//            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onResume() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_corona)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="Corona Tracker"

        coronaViewModel.loadResult("cases")

        super.onResume()

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

//        }




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
    var action = CoronaFragmentDirections.actionNavCoronaToDetailFragment(allCountryItem.country)
    findNavController().navigate(action)
}
}