package com.example.sport_planet.model.request

data class PostBoardRequest(
    val title: String,
    val content: String,
    val category: Long,
    val city: Long,
    val userTag: Long,
    val recruitNumber: Int,
    val date: String,
    val place: String
)