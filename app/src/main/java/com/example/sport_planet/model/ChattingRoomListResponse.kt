package com.example.sport_planet.model

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
        val id: Int,
        @SerializedName("hostId")
        val hostId: Int,
        @SerializedName("guestId")
        val guestId: Int,
        @SerializedName("boardId")
        val boardId: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("lastMessage")
        val lastMessage: ChattingMessageResponse,
        @SerializedName("unreadMessages")
        val unreadMessages: Int
    ): Parcelable
}