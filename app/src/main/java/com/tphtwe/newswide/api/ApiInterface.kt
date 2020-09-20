package com.tphtwe.newswide.api

import com.tphtwe.newswide.model.AllCountry
import com.tphtwe.newswide.model.allNews.All
import com.tphtwe.newswide.model.detail.CoronaDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
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
        @Query("sortBy")sortBy:String
    ):Call<All>

}