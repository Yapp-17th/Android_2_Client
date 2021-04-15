package com.yapp.domain.dto.board

import com.yapp.domain.dto.GroupStatusDto


data class BoardDto(
    val boardId: Long,
    val hostId: Long,
    val hostName: String,
    val title: String,
    val groupStatus: GroupStatusDto,
    val exercise: String,
    val city: String,
    val isBookMark: Boolean,
    val boardTime: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val startsAt: String,
    val userTag: String
)