package com.yapp.sport_planet.data.model.chatting

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