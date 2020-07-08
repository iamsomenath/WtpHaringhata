package com.sunanda.wtpharinghata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WTP_Pojo : Serializable {

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("district_code")
    @Expose
    var districtCode: String? = null

    @SerializedName("district_name")
    @Expose
    var districtName: String? = null

    @SerializedName("lab_code")
    @Expose
    var labCode: String? = null

    @SerializedName("wtp_name")
    @Expose
    var wtpName: String? = null

    @SerializedName("wtp_id")
    @Expose
    var wtpId: String? = null

    @SerializedName("scheme_name")
    @Expose
    var schemeName: String? = null

    @SerializedName("scheme_code")
    @Expose
    var schemeCode: String? = null

    @SerializedName("raw_water_source")
    @Expose
    var rawWaterSource: String? = null

    @SerializedName("clear_water_source")
    @Expose
    var clearWaterSource: String? = null

    @SerializedName("treated_water_source")
    @Expose
    var treatedWaterSource: String? = null
}