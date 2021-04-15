package com.yapp.domain.dto.login



data class LoginDto(
    val accessToken: String,
    val email: String,
    val nickName: String,
    val userId: String
)