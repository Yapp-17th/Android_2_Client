package com.example.sport_planet.data.request.board

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostBoardRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("category") val category: Long,
    @SerializedName("city") val city: Long,
    @SerializedName("userTag") val userTag: Long,
    @SerializedName("recruitNumber") val recruitNumber: Int,
    @SerializedName("date") val date: Date,
    @SerializedName("place") val place: String
)