package com.yapp.sport_planet.data.response.chatting

import com.google.gson.annotations.SerializedName


data class ChattingMessageListResponse (

    @SerializedName("transactionTime")
    var transactionTime: String,
    @SerializedName("firstUnreadMessageId")
    var firstUnreadMessageId: Int,
    @SerializedName("boardTitle")
    var boardTitle: String,
    @SerializedName("appliedStatus")
    var appliedStatus: String,
    @SerializedName("data")
    var data: List<ChattingMessageResponse>

)