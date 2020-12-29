package com.yapp.sport_planet.data.response

import com.google.gson.annotations.SerializedName

data class ServerCallBackResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)