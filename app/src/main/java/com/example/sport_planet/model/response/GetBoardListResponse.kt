package com.example.sport_planet.model.response


import com.example.sport_planet.model.BoardModel
import com.google.gson.annotations.SerializedName

data class GetBoardListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<BoardModel>
)