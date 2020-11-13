package com.example.sport_planet.model


import com.google.gson.annotations.SerializedName

data class OtherHistoryResponse(
    @SerializedName("data")
    val data: List<OtherHistoryModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("type")
    val type: String
)