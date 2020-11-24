package com.example.sport_planet.data.response.response


import com.example.sport_planet.data.response.RegionResponse
import com.google.gson.annotations.SerializedName

data class ExerciseResponse(
    @SerializedName("error")
    val error: Any,
    @SerializedName("result")
    val exerciseModel: RegionResponse
)