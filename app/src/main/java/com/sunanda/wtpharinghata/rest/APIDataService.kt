package com.sunanda.wtpharinghata.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIDataService {

    @FormUrlEncoded
    @POST("gcms-app-data-save")
    fun postData_NewParameter(@Field("gcms_wtp") value: String): Call<ResponseBody>
}