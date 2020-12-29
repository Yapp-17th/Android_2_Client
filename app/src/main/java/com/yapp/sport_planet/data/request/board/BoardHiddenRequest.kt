package com.yapp.sport_planet.data.request.board

import com.google.gson.annotations.SerializedName

data class BoardHiddenRequest(
    @SerializedName("boardId") val boardId: Long
)