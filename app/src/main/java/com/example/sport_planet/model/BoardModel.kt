package com.example.sport_planet.model

data class BoardModel(
    val boardId: Long,
    val hostId: Long,
    val hostName: String,
    val title: String,
    val groupStatus: String,
    val exercise: String,
    val city: String,
    val isBookMark: Boolean
)