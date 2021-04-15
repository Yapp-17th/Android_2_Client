package com.yapp.sport_planet.data.vo.mypage



data class HistoryVo(
        val category: List<String>,
        val city: String,
        val dislike: Long,
        val intro: String,
        val isMine: Boolean,
        val like: Long,
        val nickName: String
    )