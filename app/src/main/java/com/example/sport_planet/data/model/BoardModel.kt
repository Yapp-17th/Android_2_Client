package com.example.sport_planet.data.model

import com.example.sport_planet.data.response.GroupStatus

data class BoardModel(
    val boardId: Long,
    val hostId: Int,
    val hostName: String,
    val title: String,
    val groupStatus: GroupStatus,
    val exercise: String,
    val city: String,
    val isBookMark: Boolean,
    val boardTime: String,
    val recruitNumber: Int,
    val recruitedNumber: Int
)