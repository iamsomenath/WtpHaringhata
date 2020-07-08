package com.sunanda.wtpharinghata.viewmodel

import android.annotation.SuppressLint
import com.sunanda.wtpharinghata.rest.ApiInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModelMain : BaseViewModel() {

    var authListener: CallbackListenerMain? = null
    private var apiInterface: ApiInterface = getAPIInterface()

    fun fetchData() {
        authListener!!.onStarted()
        apiCallList()
    }

    @SuppressLint("CheckResult")
    private fun apiCallList() {

        /*val userResponseObservable = apiInterface.getDistrict()
        userResponseObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ restResponse ->
                if (restResponse.resCode == 200) {
                    authListener!!.onDistrictResponse(restResponse.data!!.toString())
                } else {
                    authListener!!.onFailure(restResponse.message!!)
                }
            }, { e ->
                authListener!!.onNetworkFailure(e.message.toString())
            })*/

        val call1 = apiInterface.getDistrict()
        call1.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                authListener!!.onNetworkFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               if(response.isSuccessful){
                   authListener!!.onDistrictResponse(response.body()!!.string())
               }else{
                   authListener!!.onFailure(response.message())
               }
            }
        })
    }

    fun fetchWTP(spDistrictID: String) {
        authListener!!.onStarted()
        apiCallWTPList(spDistrictID)
    }

    @SuppressLint("CheckResult")
    private fun apiCallWTPList(spDistrictID: String) {

        val call1 = apiInterface.getWTP(spDistrictID)
        call1.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                authListener!!.onNetworkFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    authListener!!.onWTPResponse(response.body()!!.string())
                }else{
                    authListener!!.onFailure(response.message())
                }
            }
        })
    }

    fun isValidSampleId(s: String) {
        authListener!!.onStarted()
        apiCallCheckSampleId(s)
    }

    private fun apiCallCheckSampleId(s: String) {
        val call1 = apiInterface.isValidSampleId(s)
        call1.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                authListener!!.onNetworkFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    authListener!!.onSampleIdResponse(response.body()!!.string())
                }else{
                    authListener!!.onFailure(response.message())
                }
            }
        })
    }
}