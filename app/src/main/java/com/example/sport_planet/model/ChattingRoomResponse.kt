package com.example.sport_planet.model

import android.os.Parcelable
import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChattingRoomResponse (
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
    val lastMessage: ChattingMessage

): Parcelable