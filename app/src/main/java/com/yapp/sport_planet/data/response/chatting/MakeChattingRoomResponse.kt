package com.yapp.sport_planet.data.response.chatting

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MakeChattingRoomResponse (
    @SerializedName("transactionTime")
    val transactionTime: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseType")
    val responseType: String,
    @SerializedName("data")
    val data: Data
): Parcelable {
    @Parcelize
    data class Data(
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
        @SerializedName("createdAt")
        val createdAt: String
    ): Parcelable
}