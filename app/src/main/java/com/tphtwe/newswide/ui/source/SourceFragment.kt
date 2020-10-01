package com.tphtwe.newswide.ui.source

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.source.Sources
import kotlinx.android.synthetic.main.fragment_global_detail.*
import kotlinx.android.synthetic.main.fragment_source.*


class SourceFragment : Fragment() ,SourceAdapter.ClickListener{
    lateinit var sourceAdapter: SourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar_source)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="News Channels"
        var listSource=ArrayList<Sources>()
        listSource.add(Sources("BBC-News","bbc-news"))
        listSource.add(Sources("Google-News","google-news"))
        listSource.add(Sources("ABC News","abc-news"))
        listSource.add(Sources("ESPN","espn"))
        listSource.add(Sources("FOX-News","fox-news"))
        listSource.add(Sources("Aftenposten","aftenposten"))
        listSource.add(Sources("Al Jazeera English","al-jazeera-english"))
        listSource.add(Sources("Ars Technica","ars-technica"))
        listSource.add(Sources("Ary News","ary-news"))
        listSource.add(Sources("Associated Press","associated-press"))
        listSource.add(Sources("Axios","axios"))
        listSource.add(Sources("Bloomberg","bloomberg"))
        listSource.add(Sources("Breitbart News","breitbart-news"))
        listSource.add(Sources("Business Insider","business-insider"))
        listSource.add(Sources("Buzzfeed","buzzfeed"))
        listSource.add(Sources("Cbc-News","cbc-news"))
        listSource.add(Sources("Cbs-News","cbs-news"))
        listSource.add(Sources("CNN","cnn"))
        listSource.add(Sources("Engadget","engadget"))
        listSource.add(Sources("Entertainment-Weekly","entertainment-weekly"))

        sourceAdapter=SourceAdapter(listSource)
        sourceRecycler.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=sourceAdapter
        }
        sourceAdapter.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun click(source: Sources) {
        var action=SourceFragmentDirections.actionNavSourcesToSourceNewsFragment(source.sId)
        findNavController().navigate(action)
    }
}