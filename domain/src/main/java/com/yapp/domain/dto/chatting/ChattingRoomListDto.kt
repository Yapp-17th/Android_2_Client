package com.yapp.domain.dto.chatting

data class ChattingRoomListDto(
        val id: Long,
        val hostId: Long,
        val guestId: Long,
        val boardId: Long,
        val opponentNickname: String,
        val status: String,
        val createdAt: String,
        var lastMessage: ChattingMessageDto,
        var unreadMessages: Int
    )