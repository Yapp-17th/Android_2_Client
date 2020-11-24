package com.example.sport_planet.model.request

import com.google.gson.annotations.SerializedName

data class BoardHiddenRequest(
    @SerializedName("boardId") val boardId: Long
)