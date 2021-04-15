package com.yapp.domain.dto.chatting

//@Parcelize
data class CommonServerDto(
    val transactionTime: String,
    val status: Int,
    val responseType: String,
    val message: String
)