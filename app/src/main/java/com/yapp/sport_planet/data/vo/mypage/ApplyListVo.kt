package com.yapp.sport_planet.data.vo.mypage

import com.yapp.domain.dto.ApplyStatusDto


data class ApplyListVo(
    val applyStatus: ApplyStatusDto,
    val chattingRoomId: Long,
    val guestId: Long,
    val guestName: String,
    val isHost: Boolean,
    val hostId: Long,
    val boardId: Long
)