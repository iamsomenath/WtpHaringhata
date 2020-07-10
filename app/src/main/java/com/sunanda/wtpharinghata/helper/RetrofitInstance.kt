package com.sunanda.wtpharinghata.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    private val sURL = "http://phed.sunandainternational.org/"
    private val sPHP_PATH = "api/gcms-wtp/"
    private val sMAIN_PHP_PATH = sURL + sPHP_PATH

    var BASE_URL = sMAIN_PHP_PATH

    /**
     * Create an instance of Retrofit object
     */
    val retrofitInstance3: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }
}