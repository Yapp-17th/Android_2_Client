package com.yapp.sport_planet.data.vo.login


data class SignUpVo(
    val userId: String,
    val userName: String,
    val email: String,
    val accessToken: String,
    val nickName: String,
    val address: Long,
    val category: List<Long>,
    val intro: String
)