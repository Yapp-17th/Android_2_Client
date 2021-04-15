package com.yapp.data.response

import com.google.gson.annotations.SerializedName
import com.yapp.data.model.OtherHistoryModel

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