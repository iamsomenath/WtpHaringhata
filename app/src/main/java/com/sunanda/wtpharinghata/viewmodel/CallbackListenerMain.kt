package com.sunanda.wtpharinghata.viewmodel

interface CallbackListenerMain {

    fun onStarted()
    fun onFailure(message: String)
    fun onNetworkFailure(message: String)
    fun onDistrictResponse(response: String)
    fun onWTPResponse(response: String)
    fun onSampleIdResponse(response: String)
}