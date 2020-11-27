package com.example.sport_planet.data.model.mypage

import com.example.sport_planet.data.model.BoardInfo

data class MyViewHistoryModel(
    val host : Boolean,
    val userName : String,
    val isContinue : Boolean,
    val leftTime : String,
    val boardInfo : BoardInfo
)