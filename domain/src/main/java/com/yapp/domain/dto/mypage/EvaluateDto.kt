package com.yapp.domain.dto.mypage

data class EvaluateDto(
    val userId : Long,
    val nickName : String,
    val isHost : Boolean,
    val isLike : Boolean,
    val isDislike : Boolean
)