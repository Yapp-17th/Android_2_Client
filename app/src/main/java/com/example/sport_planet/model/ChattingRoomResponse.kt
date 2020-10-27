package com.example.sport_planet.model

import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ChattingRoomResponse {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("hostId")
    @Expose
    var hostId: Int? = null

    @SerializedName("guestId")
    @Expose
    var guestId: Int? = null

    @SerializedName("boardId")
    @Expose
    var boardId: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("lastMessage")
    @Expose
    var lastMessage: ChattingMessage? = null

}