package com.yapp.sport_planet.data.vo.chatting

data class ChattingRoomListVo(
        val id: Long,
        val hostId: Long,
        val guestId: Long,
        val boardId: Long,
        val opponentNickname: String,
        val status: String,
        val createdAt: String,
        var lastMessage: ChattingMessageVo,
        var unreadMessages: Int
    )