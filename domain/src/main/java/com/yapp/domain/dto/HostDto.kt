package com.yapp.domain.dto

data class HostDto(
    val dislikes: Int,
    val hostId: Long,
    val hostName: String,
    val likes: Int,
    val intro: String
)