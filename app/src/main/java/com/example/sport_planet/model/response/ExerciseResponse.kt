package com.example.sport_planet.model.response


import com.example.sport_planet.model.ExerciseModel
import com.google.gson.annotations.SerializedName

data class ExerciseResponse(
    @SerializedName("error")
    val error: Any,
    @SerializedName("result")
    val exerciseModel: ExerciseModel
)