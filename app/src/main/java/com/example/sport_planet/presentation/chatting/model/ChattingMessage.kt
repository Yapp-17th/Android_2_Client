package com.example.sport_planet.presentation.chatting.model

import android.os.Parcelable
import com.beust.klaxon.Json
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChattingMessage (

        @SerializedName("createdAt")
        @Json(name = "createdAt")
        val timestamp: String,

        @SerializedName("senderId")
        @Json(name = "senderId")
        val senderId: Int,

        @SerializedName("content")
        @Json(name = "content")
        val content: String,

        @SerializedName("type")
        @Json(name = "type")
        val type: String,

        @SerializedName("read")
        @Json(name = "read")
        val isRead: Boolean

): Parcelable