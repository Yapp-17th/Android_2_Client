package com.example.sport_planet.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MakeChattingRoomResponse (
    @SerializedName("hostId")
    val hostId: Int,
    @SerializedName("guestId")
    val guestId: Int,
    @SerializedName("boardId")
    val boardId: Int
): Parcelable