package com.yapp.sport_planet.data.response.mypage

import com.yapp.sport_planet.data.model.mypage.MyProfileEditModel
import com.google.gson.annotations.SerializedName

data class MyProfileEditResponse(
    @SerializedName("data")
    val data: MyProfileEditModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)
