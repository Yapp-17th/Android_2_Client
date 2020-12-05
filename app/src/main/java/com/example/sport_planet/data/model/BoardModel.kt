package com.example.sport_planet.data.model

import java.util.*

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
    val startsAt: Date,
    val userTag: String
)