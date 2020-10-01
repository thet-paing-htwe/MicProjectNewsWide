package com.tphtwe.newswide.api

import com.tphtwe.newswide.model.AllCountry
import com.tphtwe.newswide.model.allNews.All
import com.tphtwe.newswide.model.coroGlobal.CoronaGlobal
import com.tphtwe.newswide.model.detail.CoronaDetail
import com.tphtwe.newswide.model.vaccine.Vaccine
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    //corona global
    val BASE_Global="https://corona.lmao.ninja/v3/covid-19/"
    //corona Baseurl
    val BASE_Country="https://corona.lmao.ninja/v2/"
    //Allnews Baseurl
    val BASE_All_News="https://newsapi.org/v2/"
    //Vaccine Tracker
    val BASE_Vaccine="https://corona.lmao.ninja/v3/covid-19/"

    var apiInterface_global:ApiInterface
    var apiInterface:ApiInterface
    var apiInterface_allNews:ApiInterface
    var apiInterface_vaccine:ApiInterface
    var apiInterface_sources:ApiInterface
    companion object{
        val Api_Key="28d11ca4baed4b118135c12f1f72a13d"
    }

    init {
        var retrofit_global=Retrofit.Builder().baseUrl(BASE_Global).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface_global=retrofit_global.create(ApiInterface::class.java)

        var retrofit_corona=Retrofit.Builder().baseUrl(BASE_Country).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface=retrofit_corona.create(ApiInterface::class.java)

        var retrofit_allNews=Retrofit.Builder().baseUrl(BASE_All_News).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface_allNews=retrofit_allNews.create(ApiInterface::class.java)

        var retrofit_vaccine=Retrofit.Builder().baseUrl(BASE_Vaccine).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface_vaccine=retrofit_vaccine.create(ApiInterface::class.java)

        var retrofit_sources=Retrofit.Builder().baseUrl(BASE_All_News).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface_sources=retrofit_sources.create(ApiInterface::class.java)

    }
    fun getGlobals(yesterdayG:Boolean):Call<CoronaGlobal>{
        return apiInterface_global.getGlobal(yesterdayG)
    }
    fun getAllCountries(sort:String):Call<AllCountry>{
        return apiInterface.getCountries(sort)
    }
    fun getDetails(country:String,yesterday:Boolean):Call<CoronaDetail>{
        return apiInterface.getCoronaDetail(country,yesterday)
    }

    fun getAllNews(search:String,sortBy:String,date1:String,date2:String):Call<All>{
        return apiInterface_allNews.getAll(Api_Key,search,sortBy,100,date1,date2)

    }
    fun getVaccineInfos():Call<Vaccine>{
        return apiInterface_vaccine.getVaccineInfo()
    }
    fun getSources(source:String,date1: String,date2:String):Call<All>{
        return apiInterface_sources.getSource(Api_Key,source,"publishedAt",40,date1,date2)
    }

}