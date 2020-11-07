package com.example.sport_planet.model

import com.example.sport_planet.model.response.GroupStatusResponse

data class BoardModel(
    val boardId: Int,
    val city: String,
    val exercise: String,
    val groupStatusResponse: GroupStatusResponse,
    val hostId: Int,
    val hostName: String,
    val isBookMark: Boolean,
    val title: String
)