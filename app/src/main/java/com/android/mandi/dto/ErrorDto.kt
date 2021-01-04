package com.android.mandi.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("status") var status : String? = "",
    @SerializedName("message") var message : String? = ""
)