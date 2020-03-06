package com.sunanda.wtpharinghata

import com.google.gson.annotations.SerializedName

class ServerResponse {

    // variable name should be same as in the json response from php
    @SerializedName("resCode")
    val success: String? = null
    @SerializedName("message")
    val message: String? = null
}
