package com.example.sport_planet.data.response


import com.example.sport_planet.data.model.MyViewHistoryModel
import com.google.gson.annotations.SerializedName

data class MyViewHistoryResponse(
    @SerializedName("data")
    val data: List<MyViewHistoryModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("type")
    val type: String
)