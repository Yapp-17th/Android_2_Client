package com.yapp.sport_planet.data.response

import com.yapp.sport_planet.data.model.OtherHistoryModel
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