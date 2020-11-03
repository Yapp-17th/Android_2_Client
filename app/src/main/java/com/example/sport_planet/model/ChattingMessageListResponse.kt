package com.example.sport_planet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ChattingMessageListResponse (

    @SerializedName("transactionTime")
    var transactionTime: String,
    @SerializedName("firstUnreadMessageId")
    var firstUnreadMessageId: Int,
    @SerializedName("data")
    var data: List<ChattingMessageResponse>

)