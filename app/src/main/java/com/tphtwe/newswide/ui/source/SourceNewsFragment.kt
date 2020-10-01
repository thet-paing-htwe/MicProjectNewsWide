package com.tphtwe.newswide.ui.source

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.allNews.Article
import com.tphtwe.newswide.ui.all.AllFragmentDirections
import com.tphtwe.newswide.ui.all.AllNewsAdapter
import com.tphtwe.newswide.ui.all.AllViewModel
import com.tphtwe.newswide.ui.all.dateFormat2
import kotlinx.android.synthetic.main.fragment_source_news.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class SourceNewsFragment : Fragment(),AllNewsAdapter.ClickListener {
   lateinit var allViewModel: AllViewModel
    lateinit var allNewsAdapter: AllNewsAdapter
    lateinit var currentDate: String
    lateinit var date: String
    lateinit var date2:String
    lateinit var sourceId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        allViewModel = ViewModelProvider(this).get(AllViewModel::class.java)
        allNewsAdapter = AllNewsAdapter()
        allNewsAdapter.setOnClickListener(this)
        sourceNewsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allNewsAdapter
        }
        observeViewModel()

    }

    fun observeViewModel() {
        allViewModel.getResultSource().observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                allNewsAdapter.updateAllNews(it.articles)
            }
        )

    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).setSupportActionBar(toolbar_source_news)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "News"

        currentDate = LocalDate.now().toString()
        date = currentDate
        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
        var cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        date2=dateFormat.format(cal.time)

        var messageArgs=arguments?.let { SourceNewsFragmentArgs.fromBundle(it) }
        sourceId=messageArgs!!.sourceId
        allViewModel.loadResultSource(sourceId,date,date2)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        c.add(Calendar.DATE,-31)

        dateBtnSource.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // Display Selected date in TextView
                    var selectedDate = "$year-0${month+1}-$dayOfMonth"
                    var formatDate= dateFormat2(selectedDate)
                    date = formatDate
                    Log.d("date", date.toString())
                    allViewModel.loadResultSource(sourceId, date, date)
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

        var action = SourceNewsFragmentDirections.actionSourceNewsFragmentToNewsDetailFragment(
            img,
            article.title,
            article.source.name,
            authorTxt,
            article.publishedAt,
            article.url
        )
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}