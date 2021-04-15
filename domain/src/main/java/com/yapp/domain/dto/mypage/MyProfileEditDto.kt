package com.yapp.domain.dto.mypage

import com.yapp.domain.dto.basic.ExerciseDto
import com.yapp.domain.dto.basic.RegionDto


data class MyProfileEditDto(
    val userId: Long,
    val userName: String,
    val nickName: String,
    val email: String,
    val intro: String,
    val category: List<ExerciseDto>,
    val city: RegionDto
)
