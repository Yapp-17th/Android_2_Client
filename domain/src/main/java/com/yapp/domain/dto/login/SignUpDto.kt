package com.yapp.domain.dto.login


data class SignUpDto(
    val userId: String,
    val userName: String,
    val email: String,
    val accessToken: String,
    val nickName: String,
    val address: Long,
    val category: List<Long>,
    val intro: String
)