package com.tphtwe.newswide.ui.category

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.headlinesNews.HeadArticle
import com.tphtwe.newswide.ui.all.dateFormat2
import com.tphtwe.newswide.ui.all.getCountry
import com.tphtwe.newswide.ui.all.getLanguage
import kotlinx.android.synthetic.main.fragment_category_news.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class CategoryNewsFragment : Fragment(),CategoryNewsAdapter.ClickListener{
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var categoryNewsAdapter: CategoryNewsAdapter
    lateinit var currentDate: String
    lateinit var date: String
    lateinit var date2:String
    lateinit var cateId:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var messageArgs=arguments?.let { CategoryNewsFragmentArgs.fromBundle(it) }
        cateId=messageArgs!!.category
        (activity as AppCompatActivity).setSupportActionBar(toolbar_category_news)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = cateId
        setHasOptionsMenu(true)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryNewsAdapter = CategoryNewsAdapter()
        categoryNewsAdapter.setOnClickListener(this)
        categoryNewsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryNewsAdapter
        }
        observeViewModel()

    }

    fun observeViewModel() {
       categoryViewModel.getResultHead().observe(
           viewLifecycleOwner, androidx.lifecycle.Observer {
               categoryNewsAdapter.updateAllNews(it.articles)
           }
       )

    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Log.d("language", getLanguage() )

        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
        var cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        date2=dateFormat.format(cal.time)

        categoryViewModel.loadResultHead(cateId,getLanguage(), getCountry())

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun click(article: HeadArticle) {
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

        var action = CategoryNewsFragmentDirections.actionCategoryNewsFragmentToNewsDetailFragment(
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