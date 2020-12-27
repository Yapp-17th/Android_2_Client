package com.yapp.sport_planet.data.model.mypage

import com.yapp.sport_planet.data.model.BoardInfo

data class MyViewHistoryModel(
    val isHost: Boolean,
    val nickName: String,
    val isContinue: Boolean,
    val boardInfo: BoardInfo
)