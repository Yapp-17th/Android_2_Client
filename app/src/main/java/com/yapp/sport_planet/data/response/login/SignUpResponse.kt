package com.yapp.sport_planet.data.response.login

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
    val address: Long,
    @SerializedName("category")
    val category: List<Long>,
    @SerializedName("intro")
    val intro: String
)