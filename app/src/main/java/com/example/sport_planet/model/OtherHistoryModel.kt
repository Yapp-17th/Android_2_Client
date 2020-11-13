package com.example.sport_planet.model

data class OtherHistoryModel(
    val isHost : Boolean,
    val userName : String,
    val isContinue : Boolean,
    val time : String,
    val boardInfo: BoardInfo
)