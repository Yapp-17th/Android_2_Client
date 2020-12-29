package com.yapp.sport_planet.data.response.common


import com.yapp.sport_planet.data.model.UserTagModel

data class UserTagResponse(
    val type: String,
    val status: Int,
    val message: String,
    val data: List<UserTagModel>
) {
    fun isSuccess(): Boolean = message == "SUCCESS"
}