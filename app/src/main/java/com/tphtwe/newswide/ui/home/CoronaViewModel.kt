package com.tphtwe.newswide.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tphtwe.newswide.api.ApiClient
import com.tphtwe.newswide.model.AllCountry
import com.tphtwe.newswide.model.coroGlobal.CoronaGlobal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoronaViewModel : ViewModel() {

   private var result:MutableLiveData<AllCountry> = MutableLiveData()
    private var result_global:MutableLiveData<CoronaGlobal> = MutableLiveData()
    private var errorMessage:MutableLiveData<String> = MutableLiveData()
    private var progressBar:MutableLiveData<Boolean> = MutableLiveData()
    private var errorStatus:MutableLiveData<Boolean> = MutableLiveData()
    private var refresh:MutableLiveData<Boolean> = MutableLiveData()

    fun getErrorMessage():LiveData<String> = errorMessage
    fun getErrorStatus():LiveData<Boolean> = errorStatus
    fun getProgress():LiveData<Boolean> = progressBar
    fun getRefresh():LiveData<Boolean> = refresh
    fun getResult() : LiveData<AllCountry> = result
    fun getResultGlobal():LiveData<CoronaGlobal> = result_global

    fun loadResult(sort:String){
        var apiClient=ApiClient()
        var call=apiClient.getAllCountries(sort)
        call.enqueue(object :Callback<AllCountry>{
            override fun onFailure(call: Call<AllCountry>, t: Throwable) {
                errorMessage.value=t.toString()
                errorStatus.value=true
                progressBar.value=false
                refresh.value=false

            }

            override fun onResponse(call: Call<AllCountry>, response: Response<AllCountry>) {
                result.value=response.body()
                errorStatus.value=false
                progressBar.value=false
                refresh.value=false
            }

        })
    }

    fun loadResultGlobal(yesterdayG:Boolean){
        var apiclient2=ApiClient()
        var call=apiclient2.getGlobals(yesterdayG)
        call.enqueue(object :Callback<CoronaGlobal>{
            override fun onFailure(call: Call<CoronaGlobal>, t: Throwable) {

            }

            override fun onResponse(call: Call<CoronaGlobal>, response: Response<CoronaGlobal>) {
                result_global.value=response.body()
            }
        })
    }
}