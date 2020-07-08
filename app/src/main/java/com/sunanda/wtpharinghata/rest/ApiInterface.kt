package com.sunanda.wtpharinghata.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {

    @FormUrlEncoded
    @POST("gcms-data")
    fun getWTP(
        @Field("district_code") district_code: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("sampleId-validate")
    fun isValidSampleId(
        @Field("sample_id") sample_id: String): Call<ResponseBody>

    @GET("district-list")
    fun getDistrict(): Call<ResponseBody>

    companion object {
        operator fun invoke(
        ): ApiInterface {
            return Retrofit.Builder()
                .baseUrl("http://phed.sunandainternational.org/api/gcms-wtp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}