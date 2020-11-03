package com.example.sport_planet.model

import android.os.Parcelable
import com.beust.klaxon.Json
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChattingMessageResponse (

    @SerializedName("id")
    @Json(name = "id")
    val id: Long,
    @SerializedName("content")
    @Json(name = "content")
    val content: String,
    @SerializedName("type")
    @Json(name = "type")
    val type: String,
    @SerializedName("hostRead")
    @Json(name = "hostRead")
    val hostRead: Boolean,
    @SerializedName("guestRead")
    @Json(name = "guestRead")
    val guestRead: Boolean,
    @SerializedName("senderId")
    @Json(name = "senderId")
    val senderId: Long,
    @SerializedName("senderNickname")
    @Json(name = "senderNickname")
    val senderNickname: String,
    @SerializedName("createdAt")
    @Json(name = "createdAt")
    val timestamp: String

): Parcelable