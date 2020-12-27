package com.yapp.sport_planet.data.response.chatting

import com.google.gson.annotations.SerializedName

data class MakeChattingMessageReadResponse (

    @SerializedName("transactionTime")
    var transactionTime: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("responseType")
    var responseType: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data: ChattingMessageResponse

)