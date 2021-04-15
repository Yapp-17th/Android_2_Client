package com.yapp.domain.dto.chatting

data class ChattingMessageDto(
    val id: Long,
    val content: String,
    val type: String,
    val realTimeUpdateType: String,
    val isHostRead: Boolean,
    val isGuestRead: Boolean,
    val messageId: Long,
    val chatRoomId: Long,
    val senderId: Long,
    val senderNickname: String,
    val createdAt: String

)