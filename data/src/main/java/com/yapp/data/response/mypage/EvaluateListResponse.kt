package com.yapp.data.response.mypage

import com.yapp.data.model.mypage.EvaluateListModel

data class EvaluateListResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<EvaluateListModel>
)