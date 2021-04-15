package com.yapp.data.request

data class MyViewEditRequest(
    val address: Long,
    val category: List<Long>,
    val email: String,
    val intro: String,
    val nickName: String,
    val userName: String
)