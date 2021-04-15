package com.yapp.data.response.mypage

import com.google.gson.annotations.SerializedName
import com.yapp.data.model.mypage.MyProfileEditModel

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
