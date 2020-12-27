package com.yapp.sport_planet.data.response.board

import com.yapp.sport_planet.data.model.BoardContentModel
import com.google.gson.annotations.SerializedName


data class BoardContentResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: BoardContentModel
)
