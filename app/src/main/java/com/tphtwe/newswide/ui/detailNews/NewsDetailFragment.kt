package com.tphtwe.newswide.ui.detailNews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.tphtwe.newswide.R
import com.tphtwe.newswide.ui.all.DateToTimeFormat
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlin.concurrent.fixedRateTimer


@Suppress("DEPRECATION")
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var messageArgs = arguments?.let { NewsDetailFragmentArgs.fromBundle(it) }
        var image = messageArgs?.image
        var title = messageArgs?.title
        var source = messageArgs?.source
        var author = messageArgs?.author
        var time = messageArgs?.time
        var url = messageArgs?.web



        webView.settings.setPluginState(WebSettings.PluginState.ON)
        webView.settings.mediaPlaybackRequiresUserGesture=true
        webView.settings.javaScriptEnabled=true
        webView.webChromeClient = WebChromeClient()
//        webView.setInitialScale(1);
        webView.settings.loadWithOverviewMode = true;
        webView.settings.useWideViewPort = true;
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, rUrl: String): Boolean {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(rUrl)
                return false // then it is not handled by default action
            }
        }
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
    override fun onResume() {
        super.onResume()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (webView.canGoBack()){
                    webView.goBack()
                }
                else{
//                    activity?.fragmentManager?.beginTransaction()?.remove()?.commit();
//                    activity?.supportFragmentManager?.beginTransaction()?.remove(this@NewsDetailFragment!!)?.commit()
//                    activity?.supportFragmentManager?.isDestroyed
//                    activity?.supportFragmentManager?.popBackStack()
                    childFragmentManager.popBackStack()
                }

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}