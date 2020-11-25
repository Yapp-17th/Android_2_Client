package com.example.sport_planet.data.request.board

import com.google.gson.annotations.SerializedName

data class BoardHiddenRequest(
    @SerializedName("boardId") val boardId: Long
)