package com.example.sport_planet.model.response


import com.google.gson.annotations.SerializedName

data class RequestCommonResponse(
    val message: String,
    val status: Int,
    val success: Boolean
)