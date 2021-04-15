package com.yapp.sport_planet.data.vo.chatting

//@Parcelize
data class CommonServerVo(
    val transactionTime: String,
    val status: Int,
    val responseType: String,
    val message: String
)