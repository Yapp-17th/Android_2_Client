package com.yapp.sport_planet.data.model

data class BoardModel(
    val boardId: Long,
    val hostId: Long,
    val hostName: String,
    val title: String,
    val groupStatus: GroupStatusModel,
    val exercise: String,
    val city: String,
    val isBookMark: Boolean,
    val boardTime: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val startsAt: String,
    val userTag: String
)