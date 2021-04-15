package com.yapp.data.response.mypage

import com.yapp.data.model.mypage.MyBookMarksModel

data class MyBookMarksResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<MyBookMarksModel>
)