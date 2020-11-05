package com.example.sport_planet.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("intro")
    val intro: String
)