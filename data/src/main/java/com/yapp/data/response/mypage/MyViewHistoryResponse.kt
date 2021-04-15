package com.yapp.data.response.mypage


import com.google.gson.annotations.SerializedName
import com.yapp.data.model.mypage.MyViewHistoryModel

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