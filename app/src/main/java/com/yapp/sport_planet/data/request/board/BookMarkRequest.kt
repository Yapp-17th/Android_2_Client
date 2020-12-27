package com.yapp.sport_planet.data.request.board

import com.google.gson.annotations.SerializedName

data class BookMarkRequest(
    @SerializedName("boardId") val boardId: Long
)