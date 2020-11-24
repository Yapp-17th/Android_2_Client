package com.example.sport_planet.data.response.response

import com.example.sport_planet.model.BoardContentModel
import com.google.gson.annotations.SerializedName


data class BoardContentResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: BoardContentModel
)