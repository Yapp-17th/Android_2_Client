package com.yapp.domain.dto.mypage

import com.yapp.domain.dto.ApplyStatusDto


data class ApplyListDto(
    val applyStatus: ApplyStatusDto,
    val chattingRoomId: Long,
    val guestId: Long,
    val guestName: String,
    val isHost: Boolean,
    val hostId: Long,
    val boardId: Long
)