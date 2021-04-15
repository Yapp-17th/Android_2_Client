package com.yapp.domain.dto.mypage



data class HistoryDto(
        val category: List<String>,
        val city: String,
        val dislike: Long,
        val intro: String,
        val isMine: Boolean,
        val like: Long,
        val nickName: String
    )