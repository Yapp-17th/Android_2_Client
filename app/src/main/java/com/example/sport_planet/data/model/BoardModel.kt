package com.example.sport_planet.data.model

import com.example.sport_planet.data.response.GroupStatus

data class BoardModel(
    val boardId: Int,
    val city: String,
    val exercise: String,
    val groupStatus: GroupStatus,
    val hostId: Int,
    val hostName: String,
    val isBookMark: Boolean,
    val title: String
)