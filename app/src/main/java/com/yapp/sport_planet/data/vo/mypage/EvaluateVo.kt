package com.yapp.sport_planet.data.vo.mypage

data class EvaluateVo(
    val userId : Long,
    val nickName : String,
    val isHost : Boolean,
    val isLike : Boolean,
    val isDislike : Boolean
)