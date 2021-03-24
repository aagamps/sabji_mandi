package com.android.propertymatch.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("status") var status : String? = "",
    @SerializedName("message") var message : String? = ""
)