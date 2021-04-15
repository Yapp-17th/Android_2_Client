package com.yapp.domain.dto.board

import com.yapp.domain.dto.GroupStatusDto
import com.yapp.domain.dto.HostDto
import com.yapp.domain.dto.basic.ExerciseDto
import com.yapp.domain.dto.basic.RegionDto
import com.yapp.domain.dto.common.UserTagDto


data class BoardContentDto(
    val boardId: Long,
    val city: RegionDto,
    val content: String,
    val exercise: ExerciseDto,
    val groupStatus: GroupStatusDto,
    val host: HostDto,
    val isBookMark: Boolean,
    val place: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val title: String,
    val boardTime: String,
    val startsAt: String,
    val userTag: UserTagDto
)
