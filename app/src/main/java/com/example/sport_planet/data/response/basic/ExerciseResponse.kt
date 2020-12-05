package com.example.sport_planet.data.response.basic


import com.example.sport_planet.data.model.CommonApiModel
import com.example.sport_planet.data.model.UserTagModel
import com.google.gson.annotations.SerializedName

data class ExerciseResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: String
) {
    data class Data(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String
    )

    fun isSuccess(): Boolean {
        return status == 200
    }
}

fun ExerciseResponse.Data.toCommon(): CommonApiModel {
    return CommonApiModel(id, name)
}