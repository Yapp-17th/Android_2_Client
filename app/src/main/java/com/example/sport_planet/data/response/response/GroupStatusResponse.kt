package com.example.sport_planet.model.response

import com.google.gson.annotations.SerializedName

data class GroupStatusResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("name") val name: String
)