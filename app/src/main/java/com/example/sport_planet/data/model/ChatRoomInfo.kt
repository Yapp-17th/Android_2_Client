package com.example.sport_planet.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoomInfo (
    val chatRoomId: Long,
    val boardId: Long,
    val guestId: Long,
    val isHost: Boolean,
    val opponentNickname: String
): Parcelable