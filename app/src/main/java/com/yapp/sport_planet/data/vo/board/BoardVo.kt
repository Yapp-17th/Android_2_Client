package com.yapp.sport_planet.data.vo.board

import com.yapp.sport_planet.data.vo.GroupStatusVo


data class BoardVo(
    val boardId: Long,
    val hostId: Long,
    val hostName: String,
    val title: String,
    val groupStatus: GroupStatusVo,
    val exercise: String,
    val city: String,
    val isBookMark: Boolean,
    val boardTime: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val startsAt: String,
    val userTag: String
)