package com.yapp.sport_planet.data.response.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("userId")
    val userId: String
)