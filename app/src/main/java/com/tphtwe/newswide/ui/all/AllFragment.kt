package com.tphtwe.newswide.ui.all

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.allNews.Article
import kotlinx.android.synthetic.main.fragment_all_news.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AllFragment : Fragment(), AllNewsAdapter.ClickListener {
//    private var searchView: SearchView? = null
//    private var queryTextListener: SearchView.OnQueryTextListener? = null

    private lateinit var allViewModel: AllViewModel
    lateinit var allNewsAdapter: AllNewsAdapter
    lateinit var currentDate: String
    lateinit var date: String
    lateinit var date2:String
    var queryText: String = "vaccine"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all_news, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allViewModel = ViewModelProvider(this).get(AllViewModel::class.java)
        allNewsAdapter = AllNewsAdapter()
        allNewsAdapter.setOnClickListener(this)
        allNewsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allNewsAdapter
        }
        observeViewModel()

    }

    fun observeViewModel() {
        allViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                allNewsAdapter.updateAllNews(it.articles)
            }
        )

    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).setSupportActionBar(toolbar_all)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "All News"

        var dateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
        var cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        date2=dateFormat.format(cal.time)

        currentDate = LocalDate.now().toString()
        date = currentDate
        allViewModel.loadResult(queryText, date, date2)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        c.add(Calendar.DATE,-31)
        swipeRefreshAN.setOnRefreshListener {
            allViewModel.loadResult(queryText,date,date2)
            observeViewModel()
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefreshAN.isRefreshing) {
                    swipeRefreshAN.isRefreshing = false
                }
            }, 5000)
        }
        swipeRefreshAN.setColorSchemeResources(R.color.materialGreen,R.color.materialBlue,R.color.materialRed)
        dateBtn.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // Display Selected date in TextView
                    var selectedDate = "$year-0${month+1}-$dayOfMonth"
                    var formatDate= dateFormat2(selectedDate)
                    date = formatDate
                    Log.d("date", date.toString())
                    allViewModel.loadResult(queryText, date, date)
                },
                year,
                month,
                day
            )
            dpd.datePicker.maxDate = System.currentTimeMillis();
            dpd.datePicker.minDate=c.timeInMillis
            dpd.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                date=currentDate
                Log.d("submit", query!!.toString())
                queryText = query
                allViewModel.loadResult(queryText!!, date, date2)
                observeViewModel()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }



    override fun click(article: Article) {
        var authorTxt = ""
        if (article.author == null) {
            authorTxt = "Anonymous"
        } else {
            authorTxt = article.author.toString()
        }

        var img: String = ""
        if (article.urlToImage == null) {
            img = "https://i.pinimg.com/736x/2c/fa/39/2cfa392e8d0b5596ac8f5bf999470ac8.jpg"
        } else {
            img = article.urlToImage
        }

        var action = AllFragmentDirections.actionNavAllToNewsDetailFragment(
            img,
            article.title,
            article.source.name,
            authorTxt,
            article.publishedAt,
            article.url
        )
        findNavController().navigate(action)
    }


}