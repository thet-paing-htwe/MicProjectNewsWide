package com.tphtwe.newswide.ui.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tphtwe.newswide.api.ApiClient
import com.tphtwe.newswide.model.allNews.All
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllViewModel : ViewModel() {

    private var result:MutableLiveData<All> = MutableLiveData()
    private var result_Source:MutableLiveData<All> = MutableLiveData()

    fun getResult():LiveData<All> = result
    fun getResultSource():LiveData<All> = result_Source

    fun loadResult(search:String,date1:String,date2:String){
        var apiClient=ApiClient()
        var call=apiClient.getAllNews(search,"publishedAt",date1,date2)
        call.enqueue(object :Callback<All>{
            override fun onFailure(call: Call<All>, t: Throwable) {

            }

            override fun onResponse(call: Call<All>, response: Response<All>) {
                result.value=response.body()
            }

        })
    }
    fun loadResultSource(source:String,date1:String,date2:String){
        var apiClientSource=ApiClient()
        var call=apiClientSource.getSources(source,date1,date2)
        call.enqueue(object :Callback<All>{
            override fun onFailure(call: Call<All>, t: Throwable) {

            }

            override fun onResponse(call: Call<All>, response: Response<All>) {
                result_Source.value=response.body()
            }

        })
    }
}