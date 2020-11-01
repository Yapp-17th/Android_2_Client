package com.example.sport_planet.model.response

import com.example.sport_planet.model.BoardModel

data class GetBoardResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<BoardModel>
)
