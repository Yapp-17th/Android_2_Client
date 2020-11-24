package com.example.sport_planet.model.response

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

