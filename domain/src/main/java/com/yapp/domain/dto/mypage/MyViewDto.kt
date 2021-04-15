package com.yapp.domain.dto.mypage


data class MyViewDto(
        val category: List<String>,
        val city: String,
        val dislike: Int,
        val info: String,
        val isMine: Boolean,
        val like: Int,
        val userName: String
)