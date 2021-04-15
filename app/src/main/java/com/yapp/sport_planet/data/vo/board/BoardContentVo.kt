package com.yapp.sport_planet.data.vo.board

import com.yapp.sport_planet.data.vo.GroupStatusVo
import com.yapp.sport_planet.data.vo.HostVo
import com.yapp.sport_planet.data.vo.basic.ExerciseVo
import com.yapp.sport_planet.data.vo.basic.RegionVo
import com.yapp.sport_planet.data.vo.common.UserTagVo


data class BoardContentVo(
    val boardId: Long,
    val city: RegionVo,
    val content: String,
    val exercise: ExerciseVo,
    val groupStatus: GroupStatusVo,
    val host: HostVo,
    val isBookMark: Boolean,
    val place: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val title: String,
    val boardTime: String,
    val startsAt: String,
    val userTag: UserTagVo
)
