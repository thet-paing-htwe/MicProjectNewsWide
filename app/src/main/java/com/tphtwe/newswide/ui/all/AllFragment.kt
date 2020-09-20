package com.tphtwe.newswide.ui.all

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
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

class AllFragment : Fragment(), AllNewsAdapter.ClickListener {
//    private var searchView: SearchView? = null
//    private var queryTextListener: SearchView.OnQueryTextListener? = null

    private lateinit var allViewModel: AllViewModel
    lateinit var allNewsAdapter: AllNewsAdapter

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("submit", query!!.toString())
                allViewModel.loadResult(query!!)
                observeViewModel()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).setSupportActionBar(toolbar_all)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="Gallery"

        allViewModel.loadResult("vaccine")
    }


    override fun click(article: Article) {
        var authorTxt=""
        if (article.author == null){
            authorTxt="Anonymous"
        }else{authorTxt=article.author.toString()}

        var img:String=""
        if(article.urlToImage==null){
            img="https://i.pinimg.com/736x/2c/fa/39/2cfa392e8d0b5596ac8f5bf999470ac8.jpg"
        }
        else{img=article.urlToImage}

        var action = AllFragmentDirections.actionNavGalleryToNewsDetailFragment(img,article.title,article.source.name,authorTxt,article.publishedAt,article.url)
        findNavController().navigate(action)
    }


}