package com.yapp.sport_planet.data.vo.mypage

import com.yapp.sport_planet.data.vo.BoardInfoVo


data class MyViewHistoryVo(
    val isHost: Boolean,
    val nickName: String,
    val isContinue: Boolean,
    val boardInfo: BoardInfoVo
)