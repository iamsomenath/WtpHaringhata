package com.sunanda.wtpharinghata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class District : Serializable{

    @SerializedName("_id")
    @Expose
    var _id: String? = null
    @SerializedName("DistrictName")
    @Expose
    var DistrictName: String? = null
    @SerializedName("District_Code")
    @Expose
    var District_Code: String? = null
}
