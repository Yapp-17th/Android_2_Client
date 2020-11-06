package com.example.sport_planet.model

import com.google.gson.annotations.SerializedName


data class ChattingMessageListResponse (

    @SerializedName("transactionTime")
    var transactionTime: String,
    @SerializedName("firstUnreadMessageId")
    var firstUnreadMessageId: Int,
    @SerializedName("boardTitle")
    var boardTitle: String,
    @SerializedName("isApplied")
    var isApplied: Boolean,
    @SerializedName("data")
    var data: List<ChattingMessageResponse>

)