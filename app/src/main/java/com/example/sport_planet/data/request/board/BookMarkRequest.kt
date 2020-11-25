package com.example.sport_planet.data.request.board

import com.google.gson.annotations.SerializedName

data class BookMarkRequest(
    @SerializedName("boardId") val boardId: Long
)