package com.example.sport_planet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ChattingRoomListResponse {
    @SerializedName("transactionTime")
    @Expose
    var transactionTime: String? = null

    @SerializedName("data")
    @Expose
    var data: List<ChattingRoomResponse>? = null

}