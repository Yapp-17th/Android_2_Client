package com.yapp.data.model.mypage


import com.google.gson.annotations.SerializedName

data class ApplyStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)