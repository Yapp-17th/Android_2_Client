package com.yapp.data.model.chatting

//@Parcelize
data class ChatRoomInfo(
    val chatRoomId: Long,
    val boardId: Long,
    val guestId: Long,
    val isHost: Boolean,
    val opponentNickname: String
)