package com.tphtwe.newswide.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tphtwe.newswide.api.ApiClient
import com.tphtwe.newswide.model.headlinesNews.Headlines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel :ViewModel(){

    private var result_headline:MutableLiveData<Headlines> = MutableLiveData()

    fun getResultHead():LiveData<Headlines> = result_headline

    fun loadResultHead(category:String,lang:String,country:String){
        var apiClient=ApiClient()
        var call=apiClient.getHeadlines(category,"publishedAt",lang,country)
        call.enqueue(object :Callback<Headlines>{
            override fun onFailure(call: Call<Headlines>, t: Throwable) {

            }

            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                result_headline.value=response.body()
            }
        })
    }
}