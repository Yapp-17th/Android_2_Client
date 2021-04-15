package com.yapp.data.response.board


import com.google.gson.annotations.SerializedName
import com.yapp.data.model.BoardModel

data class BoardListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<BoardModel>
)