package com.tphtwe.newswide.ui.vaccine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tphtwe.newswide.api.ApiClient
import com.tphtwe.newswide.model.vaccine.Vaccine
import retrofit2.Callback
import retrofit2.Response

class VaccineTrackerViewModel : ViewModel() {

    private var result:MutableLiveData<Vaccine> = MutableLiveData()

    fun getResult():LiveData<Vaccine> = result

    fun loadResult(){
        var apiClient=ApiClient()
        var call=apiClient.getVaccineInfos()
        call.enqueue(object :Callback<Vaccine>{
            override fun onFailure(call: retrofit2.Call<Vaccine>, t: Throwable) {

            }

            override fun onResponse(call: retrofit2.Call<Vaccine>, response: Response<Vaccine>) {
                result.value=response.body()
            }

        })
    }
}