package com.example.sport_planet.model

data class BoardContent(
    val boardId: Long,
    val title: String,
    val groupStatus: String,
    val exercise: String,
    val city: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val content: String,
    val isBookMark: Boolean,
    val host: Host
)
