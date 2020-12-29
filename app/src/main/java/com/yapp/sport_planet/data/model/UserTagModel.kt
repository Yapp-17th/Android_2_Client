package com.yapp.sport_planet.data.model

data class UserTagModel(
    val id: Long,
    val name: String
)

fun UserTagModel.toCommon(): CommonApiModel {
    return CommonApiModel(id, name)
}