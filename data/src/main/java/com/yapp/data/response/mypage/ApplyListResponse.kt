package com.yapp.data.response.mypage

import com.yapp.data.model.mypage.ApplyListModel

data class ApplyListResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ApplyListModel>
)