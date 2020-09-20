package com.tphtwe.newswide.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tphtwe.newswide.api.ApiClient
import com.tphtwe.newswide.model.detail.CoronaDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel :ViewModel(){
    var result:MutableLiveData<CoronaDetail> = MutableLiveData()
    private var errorMessage:MutableLiveData<String> = MutableLiveData()
    private var progressBar:MutableLiveData<Boolean> = MutableLiveData()
    private var errorStatus:MutableLiveData<Boolean> = MutableLiveData()

    fun getErrorMessage():LiveData<String> = errorMessage
    fun getErrorStatus():LiveData<Boolean> = errorStatus
    fun getProgress():LiveData<Boolean> = progressBar
    fun getResult(): LiveData<CoronaDetail> = result

    fun loadResult(country:String,yesterday:Boolean){
        var apiClient=ApiClient()
        var call=apiClient.getDetails(country,yesterday)
        call.enqueue(object :Callback<CoronaDetail>{
            override fun onFailure(call: Call<CoronaDetail>, t: Throwable) {
                errorMessage.value=t.toString()
                errorStatus.value=true
                progressBar.value=false

            }

            override fun onResponse(call: Call<CoronaDetail>, response: Response<CoronaDetail>) {
                result.value=response.body()
                errorStatus.value= false
                progressBar.value=false
            }

        })
    }
}