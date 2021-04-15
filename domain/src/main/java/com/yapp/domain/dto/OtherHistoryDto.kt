package com.yapp.domain.dto

data class OtherHistoryDto(
    val isHost: Boolean,
    val nickName: String,
    val isContinue: Boolean,
    val boardInfo: BoardInfoDto
)