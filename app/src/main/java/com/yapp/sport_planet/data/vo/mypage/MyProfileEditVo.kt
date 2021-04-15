package com.yapp.sport_planet.data.vo.mypage

import com.yapp.domain.dto.basic.RegionDto
import com.yapp.sport_planet.data.vo.basic.ExerciseVo
import com.yapp.sport_planet.data.vo.basic.RegionVo


data class MyProfileEditVo(
    val userId: Long,
    val userName: String,
    val nickName: String,
    val email: String,
    val intro: String,
    val category: List<ExerciseVo>,
    val city: RegionVo
)
