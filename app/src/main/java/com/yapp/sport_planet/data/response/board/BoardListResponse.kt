package com.yapp.sport_planet.data.response.board


import com.yapp.sport_planet.data.model.BoardModel
import com.google.gson.annotations.SerializedName

data class BoardListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<BoardModel>
)