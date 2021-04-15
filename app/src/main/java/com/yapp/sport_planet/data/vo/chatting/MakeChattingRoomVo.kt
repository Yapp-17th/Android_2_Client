package com.yapp.sport_planet.data.vo.chatting

//@Parcelize
data class MakeChattingRoomVo(
        val id: Long,
        val hostId: Long,
        val guestId: Long,
        val boardId: Long,
        val opponentNickname: String,
        val createdAt: String
)