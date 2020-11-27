package com.example.sport_planet.data.response.chatting

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
        val createdAt: String
    ): Parcelable
}