package com.yapp.data.response.chatting

import com.google.gson.annotations.SerializedName

//@Parcelize
data class CommonServerResponse(
    @SerializedName("transactionTime")
    val transactionTime: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseType")
    val responseType: String,
    @SerializedName("message")
    val message: String
)