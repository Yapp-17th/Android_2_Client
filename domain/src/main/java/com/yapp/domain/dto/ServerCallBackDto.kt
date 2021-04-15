package com.yapp.domain.dto

data class ServerCallBackDto(
    val message: String,
    val status: Int,
    val success: Boolean
)