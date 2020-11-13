package com.example.sport_planet.data.response


import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
){
    data class Data(
        @SerializedName("category")
        val category: List<String>,
        @SerializedName("city")
        val city: String,
        @SerializedName("dislike")
        val dislike: Int,
        @SerializedName("info")
        val info: String,
        @SerializedName("isMine")
        val isMine: Boolean,
        @SerializedName("like")
        val like: Int,
        @SerializedName("userName")
        val userName: String
    )
}