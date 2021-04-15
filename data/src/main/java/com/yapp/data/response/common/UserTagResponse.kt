package com.yapp.data.response.common


import com.yapp.data.model.UserTagModel

data class UserTagResponse(
    val type: String,
    val status: Int,
    val message: String,
    val data: List<UserTagModel>
) {
//    fun isSuccess(): Boolean = message == "SUCCESS"
}