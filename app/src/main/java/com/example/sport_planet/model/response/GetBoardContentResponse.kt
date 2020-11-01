package com.example.sport_planet.model.response

import com.example.sport_planet.model.BoardContent

data class GetBoardContentResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: BoardContent
)


