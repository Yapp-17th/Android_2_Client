package com.yapp.data.model.mypage

import com.yapp.data.model.BoardInfo

data class MyViewHistoryModel(
    val isHost: Boolean,
    val nickName: String,
    val isContinue: Boolean,
    val boardInfo: BoardInfo
)