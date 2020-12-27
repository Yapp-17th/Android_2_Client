package com.yapp.sport_planet.data.response.chatting

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ChattingMessageResponse (

    @SerializedName("id")
    val id: Long,
    @SerializedName("content")
    val content: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("realTimeUpdateType")
    val realTimeUpdateType: String,
    @SerializedName("isHostRead")
    val isHostRead: Boolean,
    @SerializedName("isGuestRead")
    val isGuestRead: Boolean,
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("chatRoomId")
    val chatRoomId: Long,
    @SerializedName("senderId")
    val senderId: Long,
    @SerializedName("senderNickname")
    val senderNickname: String,
    @SerializedName("createdAt")
    val createdAt: String

): Parcelable