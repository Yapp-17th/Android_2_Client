package com.yapp.domain.dto.chatting

//@Parcelize
data class MakeChattingRoomDto(
        val id: Long,
        val hostId: Long,
        val guestId: Long,
        val boardId: Long,
        val opponentNickname: String,
        val createdAt: String
)