package com.yapp.sport_planet.data.response.chatting

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ChattingRoomListResponse (
    @SerializedName("transactionTime")
    val transactionTime: String,
    @SerializedName("data")
    var data: List<Data>
) {
    @Parcelize
    data class Data (
        @SerializedName("id")
        val id: Long,
        @SerializedName("hostId")
        val hostId: Long,
        @SerializedName("guestId")
        val guestId: Long,
        @SerializedName("boardId")
        val boardId: Long,
        @SerializedName("opponentNickname")
        val opponentNickname: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("lastMessage")
        var lastMessage: ChattingMessageResponse,
        @SerializedName("unreadMessages")
        var unreadMessages: Int
    ): Parcelable
}