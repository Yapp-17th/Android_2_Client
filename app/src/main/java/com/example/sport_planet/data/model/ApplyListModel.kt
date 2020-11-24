package com.example.sport_planet.data.model

import com.google.gson.annotations.SerializedName

data class ApplyListModel(
        @SerializedName("applyStatus")
        val applyStatus: ApplyStatus,
        @SerializedName("chattingRoomId")
        val chattingRoomId: Int,
        @SerializedName("guestId")
        val guestId: Int,
        @SerializedName("guestName")
        val guestName: String,
        @SerializedName("host")
        val host: Boolean,
        @SerializedName("hostId")
        val hostId: Int
    )
