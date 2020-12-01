package com.example.sport_planet.data.response.common


import com.example.sport_planet.data.model.ExerciseModel

data class ExerciseResponse(
    val type: String,
    val status: Int,
    val message: String,
    val data: List<ExerciseModel>
) {
    fun isSuccess(): Boolean = message == "SUCCESS"
}