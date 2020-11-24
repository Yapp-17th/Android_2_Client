package com.example.sport_planet.model

import com.google.gson.annotations.SerializedName

data class HostModel(
    @SerializedName("dislikes") val dislikes: Int,
    @SerializedName("hostId") val hostId: Int,
    @SerializedName("hostName") val hostName: String,
    @SerializedName("likes") val likes: Int
)