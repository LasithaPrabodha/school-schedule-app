package com.capstoneprojectg8.schoolscheduleapp.models

import com.google.gson.annotations.SerializedName

data class University(
    @SerializedName("alpha_two_code") var alphaTwoCode: String? = null,
    @SerializedName("web_pages") var webPages: ArrayList<String> = arrayListOf(),
    @SerializedName("state-province") var stateProvince: String? = null,
    var name: String? = null,
    var domains: ArrayList<String> = arrayListOf(),
    var country: String? = null
)
