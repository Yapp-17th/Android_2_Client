package com.example.sport_planet.data.response

import com.google.gson.annotations.SerializedName

data class GroupStatus(
    @SerializedName("code") val code: Int,
    @SerializedName("name") val name: String
)