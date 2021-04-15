package com.yapp.data.response.board

import com.google.gson.annotations.SerializedName
import com.yapp.data.model.BoardContentModel


data class BoardContentResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: BoardContentModel
)
