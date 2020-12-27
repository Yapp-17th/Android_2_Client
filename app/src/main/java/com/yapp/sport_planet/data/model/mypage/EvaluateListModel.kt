package com.yapp.sport_planet.data.model.mypage

data class EvaluateListModel(
    val userId : Long,
    val nickName : String,
    val isHost : Boolean,
    val isLike : Boolean,
    val isDislike : Boolean
)