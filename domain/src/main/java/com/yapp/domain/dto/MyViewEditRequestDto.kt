package com.yapp.domain.dto

data class MyViewEditRequestDto(
    val address: Long,
    val category: List<Long>,
    val email: String,
    val intro: String,
    val nickName: String,
    val userName: String
)