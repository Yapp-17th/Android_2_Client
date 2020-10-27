package com.example.sport_planet.model

import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ChattingMessageListResponse {

    @SerializedName("transactionTime")
    @Expose
    var transactionTime: String? = null

    @SerializedName("firstUnreadMessageId")
    @Expose
    var firstUnreadMessageId: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<ChattingMessage>? = null

}