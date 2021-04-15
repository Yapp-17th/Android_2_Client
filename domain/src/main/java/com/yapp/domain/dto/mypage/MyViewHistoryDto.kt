package com.yapp.domain.dto.mypage

import com.yapp.domain.dto.BoardInfoDto


data class MyViewHistoryDto(
    val isHost: Boolean,
    val nickName: String,
    val isContinue: Boolean,
    val boardInfo: BoardInfoDto
)