package com.example.sport_planet.data.model


import com.google.gson.annotations.SerializedName

data class ApplyStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)