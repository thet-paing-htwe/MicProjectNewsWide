package com.tphtwe.newswide.ui.vaccine

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import kotlinx.android.synthetic.main.fragment_vaccine_tracker.*

class VaccineTrackerFragment : Fragment() {

    private lateinit var vaccineTrackerViewModel: VaccineTrackerViewModel
    lateinit var vaccineAdapter: VaccineAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_vaccine_tracker, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaccineTrackerViewModel=ViewModelProvider(this).get(VaccineTrackerViewModel::class.java)
        vaccineAdapter= VaccineAdapter()
        vaccineRecycler.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=vaccineAdapter
        }
        observeViewModel()
    }
    fun observeViewModel(){
        vaccineTrackerViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                vaccineAdapter.updateVaccine(it.data)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setSupportActionBar(toolbar_vaccine)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="Vaccine Tracker"
        vaccineTrackerViewModel.loadResult()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}