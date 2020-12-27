package com.yapp.sport_planet.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ApplyListModel(
    @SerializedName("applyStatus")
    val applyStatus: ApplyStatus,
    @SerializedName("chattingRoomId")
    val chattingRoomId: Long,
    @SerializedName("guestId")
    val guestId: Long,
    @SerializedName("guestName")
    val guestName: String,
    @SerializedName("isHost")
    val isHost: Boolean,
    @SerializedName("hostId")
    val hostId: Long,
    @SerializedName("boardId")
    val boardId: Long
)
