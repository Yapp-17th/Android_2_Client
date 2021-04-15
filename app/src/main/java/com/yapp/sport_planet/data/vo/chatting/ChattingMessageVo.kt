package com.yapp.sport_planet.data.vo.chatting

data class ChattingMessageVo(
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