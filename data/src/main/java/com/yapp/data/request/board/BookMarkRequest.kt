package com.yapp.data.request.board

import com.google.gson.annotations.SerializedName

data class BookMarkRequest(
    @SerializedName("boardId") val boardId: Long
)