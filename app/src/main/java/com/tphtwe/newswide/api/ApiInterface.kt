package com.tphtwe.newswide.api

import com.tphtwe.newswide.model.AllCountry
import com.tphtwe.newswide.model.allNews.All
import com.tphtwe.newswide.model.coroGlobal.CoronaGlobal
import com.tphtwe.newswide.model.detail.CoronaDetail
import com.tphtwe.newswide.model.headlinesNews.Headlines
import com.tphtwe.newswide.model.vaccine.Vaccine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    //corona global
    @GET("all")
    fun getGlobal(
        @Query("yesterday")yesterdayG:Boolean
    ):Call<CoronaGlobal>

    //corona all country view
    @GET("countries")
    fun getCountries(
        @Query("sort")sort:String
    ):Call<AllCountry>

    //corona detail view
    @GET("countries/{country}")
    fun getCoronaDetail(
        @Path("country")country:String,
        @Query("yesterday")yesterday:Boolean
    ):Call<CoronaDetail>

    //news all view
    @GET("everything")
    fun getAll(
        @Query("apiKey")apiKey:String,
        @Query("q")search:String,
        @Query("sortBy")sortBy:String,
        @Query("pageSize")size:Int,
        @Query("from")date1:String,
        @Query("to")date2:String

    ):Call<All>
    //news all source
    @GET("everything")
    fun getSource(
        @Query("apiKey")apiKey:String,
        @Query("sources")source:String,
        @Query("sortBy")sortBy:String,
        @Query("pageSize")size:Int,
        @Query("from")date1:String,
        @Query("to")date2:String

    ):Call<All>

//new headlines
    @GET("top-headlines")
    fun getHedline(
    @Query("apiKey")apiKey:String,
    @Query("category")category:String,
    @Query("sortBy")sortBy:String,
    @Query("pageSize")size:Int,
    @Query("language")lang:String,
    @Query("country")country:String
):Call<Headlines>

    //vaccine tracker
    @GET("therapeutics")
    fun  getVaccineInfo():Call<Vaccine>
}
