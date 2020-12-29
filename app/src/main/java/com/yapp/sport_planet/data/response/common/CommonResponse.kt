package com.yapp.sport_planet.data.response.common

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)