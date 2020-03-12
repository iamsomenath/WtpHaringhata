package com.sunanda.wtpharinghata.helper

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIDataService {

    @FormUrlEncoded
    @POST("postData_NewParameter")
    fun postData_NewParameter(@Field("val") value: String): Call<ResponseBody>
}