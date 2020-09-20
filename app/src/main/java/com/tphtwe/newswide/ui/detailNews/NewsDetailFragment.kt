package com.tphtwe.newswide.ui.detailNews

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.ui.all.DateToTimeFormat
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlin.math.abs


class NewsDetailFragment : Fragment() {
    var isHideToolbarView = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail_news, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var messageArgs = arguments?.let { NewsDetailFragmentArgs.fromBundle(it) }
        var image = messageArgs?.image
        var title = messageArgs?.title
        var source = messageArgs?.source
        var author = messageArgs?.author
        var time = messageArgs?.time
        var url = messageArgs?.web


        webView.loadUrl(url!!)
        Picasso.get().load(image).placeholder(R.color.materialGreen).into(collapseImage)
        newsTitle?.text = title
        newsSource?.text = source
        newsAuthor?.text = author
        newsAgo?.text = DateToTimeFormat(time)

        (activity as AppCompatActivity).setSupportActionBar(toolbar_detail_news)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = source


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}